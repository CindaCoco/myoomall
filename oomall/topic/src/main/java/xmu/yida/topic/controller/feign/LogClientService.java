package xmu.yida.topic.controller.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import xmu.yida.topic.controller.fallback.LogClientServiceFallbackFactory;
import xmu.yida.topic.domain.Log;

@FeignClient(value = "LOGSERVICE",fallbackFactory = LogClientServiceFallbackFactory.class)
public interface LogClientService {
    @PostMapping("/logs")
    public Object addLog(Log log);
}
