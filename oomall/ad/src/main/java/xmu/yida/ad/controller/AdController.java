package xmu.yida.ad.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.yida.ad.domain.Ad;
import xmu.yida.ad.service.AdService;
import xmu.yida.ad.util.ResponseUtil;

/**
 * @Author zjy
 * @create 2019/12/8 23:46
 */
@RestController
public class AdController {

    @Autowired
    AdService adService;

    @GetMapping("/admins/ads")
    public Object adminFindAdList(@RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer limit
    ){
        return ResponseUtil.ok(adService.getAllAds(page,limit));
    }
    @PostMapping("/ads")
    public Object adminCreateAd(@RequestBody Ad ad){
        System.out.println(ad);
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
    public Object userFindAdList(@RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer limit){
        return ResponseUtil.ok(adService.getAllAds(page,limit));
    }


    public Object processHystrixId(@PathVariable Integer id){
        return ResponseUtil.timeOut();
    }
}
