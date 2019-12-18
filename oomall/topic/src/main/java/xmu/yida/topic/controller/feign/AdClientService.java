package xmu.yida.topic.controller.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import xmu.yida.topic.controller.fallback.AdClientServiceFallbackFactory;

/**
 * @author LYD
 */
@FeignClient(value="ADSERVICE",fallbackFactory = AdClientServiceFallbackFactory.class)
public interface AdClientService {
    @RequestMapping(value="/ads",method = RequestMethod.GET)
    public Object getAds();

    @RequestMapping(value="/ads/{id}",method = RequestMethod.GET)
    public Object getAdById(@PathVariable Integer id);

    @DeleteMapping("/ads/{id}")
    public Object deleteAdById(@PathVariable Integer id);

}
