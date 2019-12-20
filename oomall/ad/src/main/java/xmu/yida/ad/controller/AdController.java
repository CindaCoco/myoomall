package xmu.yida.ad.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.yida.ad.domain.Ad;
import xmu.yida.ad.service.AdService;
import xmu.yida.ad.util.ResponseUtil;

import java.util.HashMap;

/**
 * @Author zjy
 * @create 2019/12/8 23:46
 */
@RestController
public class AdController {

    @Autowired
    AdService adService;

    @GetMapping("/admins/ads")
    public Object adminFindAdList(
            @RequestParam(name = "name",required = false) String name,
            @RequestParam(name = "adContent",required = false) String adContent,
            @RequestParam(name="page",defaultValue = "1") Integer page,
            @RequestParam(name="limit",defaultValue = "10") Integer limit
    ){
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("name",name);
        map.put("content",adContent);
        return ResponseUtil.ok(adService.getAllAds(page,limit,map));
    }
    @PostMapping("/ads")
    public Object adminCreateAd(@RequestBody Ad ad){
        Ad retAd=adService.addAd(ad);
        if(retAd==null){
            return ResponseUtil.createFailed();
        }else{
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
    public Object adminUpdateAd(@PathVariable Integer id, @RequestBody Ad ad){
        if(ad.getId().equals(id)){
            Ad retAd=adService.updateAd(ad);
            if(retAd==null){
                return ResponseUtil.updatedDataFailed();
            }else{
                return  ResponseUtil.ok(retAd);
            }
        }else{
            return ResponseUtil.badArgumentValue();
        }
    }

    @DeleteMapping("/ads/{id}")
    public Object adminDeleteAd(@PathVariable Integer id){
        boolean success=adService.deleteAdById(id);
        if(success){
            return ResponseUtil.ok();
        }else{
            return ResponseUtil.fail();
        }
    }

    @GetMapping("/ads")
    public Object userFindAdList(){
        return ResponseUtil.ok(adService.userGetAllAds());
    }


    public Object processHystrixId(@PathVariable Integer id){
        return ResponseUtil.timeOut();
    }
}
