package xmu.yida.ad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;
import xmu.yida.ad.controller.feign.LogClientService;
import xmu.yida.ad.domain.Ad;
import xmu.yida.ad.domain.Log;
import xmu.yida.ad.service.AdService;
import xmu.yida.ad.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author LYD
 */
@RestController
public class AdController {

    @Autowired
    AdService adService;

    @Autowired
    LogClientService logClientService;


    private final static Integer SELECT=0;
    private final static Integer INSERT=1;
    private final static Integer UPDATE=2;
    private final static Integer DELETE=3;



    @GetMapping(value = "/admins/ads",produces = "application/json;charset=utf-8")
    public Object adminFindAdList(
            @RequestParam(name = "name",required = false) String name,
            @RequestParam(name = "adContent",required = false) String adContent,
            @RequestParam(name="page",defaultValue = "1") Integer page,
            @RequestParam(name="limit",defaultValue = "10") Integer limit,
            HttpServletRequest request
    ){
        Integer adminId=getUserId(request);
        if(adminId==null){
            return ResponseUtil.fail(668,"管理员未登录");
        }
        String ip=request.getHeader("ip");
        if(page<=0||limit<=0){
            return ResponseUtil.fail(680,"获取广告失败");
        }
        HashMap<String,Object> map=new HashMap<>(2);
        map.put("name",name);
        map.put("content",adContent);
        Object retObj=adService.getAllAds(page,limit,map);
        if(retObj!=null){
            createLog(adminId,ip,SELECT,"查询广告列表",1,null);
            return ResponseUtil.ok(retObj);
        }else {
            createLog(adminId,ip,SELECT,"查询广告列表",0,null);
            return ResponseUtil.fail(680,"获取广告失败");
        }

    }


    @PostMapping(value = "/ads",produces = "application/json;charset=utf-8")
    public Object adminCreateAd(@RequestBody Ad ad,HttpServletRequest request){
        Integer adminId=getUserId(request);
        if(adminId==null){
            return ResponseUtil.fail(668,"管理员未登录");
        }
        String ip=request.getHeader("ip");

        Ad retAd=adService.addAd(ad);
        if(retAd==null){
            createLog(adminId,ip,INSERT,"创建广告",0,null);
            return ResponseUtil.fail(681,"创建广告失败");
        }else{
            createLog(adminId,ip,INSERT,"创建广告",1,retAd.getId());
            return ResponseUtil.ok(retAd);
        }
    }

    @GetMapping(value = "/ads/{id}",produces = "application/json;charset=utf-8")
    public Object findAdById(@PathVariable Integer id){
        if(id==null||id<=0){
            return ResponseUtil.fail(580,"参数不合法");
        }
        Ad retAd=adService.getAdById(id);
        if(retAd==null){
            return ResponseUtil.fail(680,"获取广告失败");
        }else{
            return ResponseUtil.ok(retAd);
        }
    }

    @GetMapping(value = "/admin/ads/{id}",produces = "application/json;charset=utf-8")
    public Object adminFindAdById(@PathVariable Integer id, HttpServletRequest request){
        Integer adminId=getUserId(request);
        if(adminId==null){
            return ResponseUtil.fail(668,"管理员未登录");
        }
        String ip=request.getHeader("ip");
        if(id==null||id<=0){
            return ResponseUtil.fail(580,"参数不合法");
        }
        Ad retAd=adService.getAdById(id);
        if(retAd==null){
            createLog(adminId,ip,SELECT,"获取广告",0,id);
            return ResponseUtil.fail(680,"获取广告失败");
        }else{
            createLog(adminId,ip,SELECT,"获取广告",1,id);
            return ResponseUtil.ok(retAd);
        }
    }

    @PutMapping(value = "/ads/{id}",produces = "application/json;charset=utf-8")
    public Object adminUpdateAd(@PathVariable Integer id, @RequestBody Ad ad,HttpServletRequest request){
        Integer adminId=getUserId(request);
        if(adminId==null){
            return ResponseUtil.fail(668,"管理员未登录");
        }
        Ad targetAd=adService.getAdById(id);
        if(id==null||id<=0){
            return ResponseUtil.fail(682,"修改广告失败");
        }
        String ip=request.getHeader("ip");
        if(targetAd.getStartTime()!=null&&targetAd.getEndTime()!=null){
            if(targetAd.getStartTime().isAfter(targetAd.getEndTime())){
                return ResponseUtil.fail(682,"修改广告失败");
            }
        }
        ad.setId(id);
        Ad retAd=adService.updateAd(ad);
        if(retAd==null){
            createLog(adminId,ip,UPDATE,"修改广告",0,id);
            return ResponseUtil.fail(682,"修改广告失败");
        }else{
            createLog(adminId,ip,UPDATE,"修改广告",1,id);
            return ResponseUtil.ok(retAd);
        }
    }

    @DeleteMapping("/ads/{id}")
    public Object adminDeleteAd(@PathVariable Integer id,HttpServletRequest request){
        Integer adminId=getUserId(request);
        if(adminId==null){
            return ResponseUtil.fail(668,"管理员未登录");
        }
        String ip=request.getHeader("ip");
        if(id==null||id<=0){
            return ResponseUtil.fail(683,"删除广告失败");
        }
        boolean success=adService.deleteAdById(id);
        if(success){
            createLog(adminId,ip,DELETE,"删除广告",1,id);
            return ResponseUtil.ok();
        }else{
            createLog(adminId,ip,DELETE,"删除广告",0,id);
            return ResponseUtil.fail(683,"删除广告失败");
        }
    }

    @GetMapping(value = "/ads",produces = "application/json;charset=utf-8")
    public Object userFindAdList(){
        return ResponseUtil.ok(adService.userGetAllAds());
    }



    private void createLog(Integer adminId,String ip,Integer type,String actions,
                           Integer statusCode,Integer actionId){
        Log log=new Log();
        log.setAdminId(adminId);
        log.setIp(ip);
        log.setType(type);
        log.setActions(actions);
        log.setStatusCode(statusCode);
        log.setActionId(actionId);
        logClientService.addLog(log);
    }

    private Integer getUserId(HttpServletRequest request){
        String userId=request.getHeader("userid");
        if(userId==null){
            return null;
        }
        return Integer.valueOf(userId);
    }

}
