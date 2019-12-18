package xmu.yida.topic.controller.fallback;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import xmu.yida.topic.controller.feign.AdClientService;
import xmu.yida.topic.util.ResponseUtil;

/**
 * @author LYD
 */
@Component
public class AdClientServiceFallbackFactory implements FallbackFactory<AdClientService> {

    @Override
    public AdClientService create(Throwable throwable) {
        return new AdClientService() {
            @Override
            public Object getAds() {
                return ResponseUtil.clientShutdown();
            }

            @Override
            public Object getAdById(Integer id) {
                return ResponseUtil.clientShutdown();
            }

            @Override
            public Object deleteAdById(Integer id) {
                return ResponseUtil.clientShutdown();
            }
        };
    }
}
