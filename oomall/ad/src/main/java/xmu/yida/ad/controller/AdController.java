package xmu.yida.ad.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.yida.ad.controller.feign.LogClientService;
import xmu.yida.ad.domain.Ad;
import xmu.yida.ad.domain.Log;
import xmu.yida.ad.service.AdService;
import xmu.yida.ad.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @Author zjy
 * @create 2019/12/8 23:46
 */
@RestController
public class AdController {

    @Autowired
    AdService adService;

    @Autowired
    LogClientService logClientService;

    @GetMapping("/admins/ads")
    public Object adminFindAdList(
            @RequestParam(name = "name",required = false) String name,
            @RequestParam(name = "adContent",required = false) String adContent,
            @RequestParam(name="page",defaultValue = "1") Integer page,
            @RequestParam(name="limit",defaultValue = "10") Integer limit,
            HttpServletRequest request
    ){
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("name",name);
        map.put("content",adContent);
        Object retObj=adService.getAllAds(page,limit,map);
        if(retObj!=null){
            Log log=new Log(request.getIntHeader("userId"),
                    request.getHeader("ip"),0,"查询广告列表",1,null);
            logClientService.addLog(log);
            return ResponseUtil.ok(retObj);
        }else {
            Log log=new Log(request.getIntHeader("userId"),
                    request.getHeader("ip"),0,"查询广告列表",0,null);
            logClientService.addLog(log);
            return ResponseUtil.fail();
        }

    }
    @PostMapping("/ads")
    public Object adminCreateAd(@RequestBody Ad ad,HttpServletRequest request){
        Ad retAd=adService.addAd(ad);
        if(retAd==null){
            Log log=new Log(request.getIntHeader("userId"),
                    request.getHeader("ip"),1,"增加广告",0,null);
            logClientService.addLog(log);
            return ResponseUtil.createFailed();
        }else{
            Log log=new Log(request.getIntHeader("userId"),
                    request.getHeader("ip"),1,"增加广告",1,null);
            logClientService.addLog(log);
            return ResponseUtil.ok(retAd);
        }
    }
    @HystrixCommand(fallbackMethod = "processHystrixId")
    @GetMapping("/ads/{id}")
    public Object adminFindAdById(@PathVariable Integer id){
        Ad retAd=adService.getAdById(id);
        if(retAd==null){
            return ResponseUtil.notFound();
        }else{
            return ResponseUtil.ok(retAd);
        }
    }
    @PutMapping("/ads/{id}")
    public Object adminUpdateAd(@PathVariable Integer id, @RequestBody Ad ad,HttpServletRequest request){
            ad.setId(id);
            Ad retAd=adService.updateAd(ad);
            if(retAd==null){
                Log log=new Log(request.getIntHeader("userId"),
                        request.getHeader("ip"),2,"修改广告",0,id);
                logClientService.addLog(log);
                return ResponseUtil.updatedDataFailed();
            }else{
                Log log=new Log(request.getIntHeader("userId"),
                        request.getHeader("ip"),2,"修改广告",1,id);
                logClientService.addLog(log);
                return  ResponseUtil.ok(retAd);
            }
    }

    @DeleteMapping("/ads/{id}")
    public Object adminDeleteAd(@PathVariable Integer id,HttpServletRequest request){
        boolean success=adService.deleteAdById(id);
        if(success){
            Log log=new Log(request.getIntHeader("userId"),
                    request.getHeader("ip"),3,"删除广告",1,id);
            logClientService.addLog(log);
            return ResponseUtil.ok();
        }else{
            Log log=new Log(request.getIntHeader("userId"),
                    request.getHeader("ip"),3,"删除广告",0,id);
            logClientService.addLog(log);
            return ResponseUtil.fail();
        }
    }

    @GetMapping("/ads")
    public Object userFindAdList(){
        return ResponseUtil.ok(adService.userGetAllAds());
    }


    public Object processHystrixId(@PathVariable Integer id){
        return ResponseUtil.fail();
    }
}
