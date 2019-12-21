package xmu.yida.ad.controller.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import xmu.yida.ad.controller.fallback.LogClientServiceFallbackFactory;
import xmu.yida.ad.domain.Log;

@FeignClient(value = "LOGSERVICE",fallbackFactory = LogClientServiceFallbackFactory.class)
public interface LogClientService {
    @PostMapping("/log")
    public Object addLog(Log log);
}
