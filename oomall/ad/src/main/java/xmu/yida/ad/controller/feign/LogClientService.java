package xmu.yida.ad.controller.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import xmu.yida.ad.controller.fallback.LogClientServiceFallbackFactory;
import xmu.yida.ad.domain.Log;

/**
 * @author LYD
 */
@FeignClient(value = "LOGSERVICE",fallbackFactory = LogClientServiceFallbackFactory.class)
public interface LogClientService {

    /**
     * 调用外部日志接口
     * @param log 要添加的日志
     * @return 添加结果
     */
    @PostMapping("/log")
    public Object addLog(Log log);
}
