package xmu.yida.topic.controller.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import xmu.yida.topic.controller.fallback.LogClientServiceFallbackFactory;
import xmu.yida.topic.domain.Log;

/**
 * @author LYD
 */
@FeignClient(value = "LOGSERVICE",fallbackFactory = LogClientServiceFallbackFactory.class,url = "http://123.56.30.118:8003")
public interface LogClientService {

    /**
     * 调用外部日志接口
     * @param log 要添加的日志
     * @return 添加结果
     */
    @PostMapping("/log")
    public Object addLog(Log log);
}
