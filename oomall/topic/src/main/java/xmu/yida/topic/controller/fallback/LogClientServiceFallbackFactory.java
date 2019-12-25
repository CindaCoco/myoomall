package xmu.yida.topic.controller.fallback;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import xmu.yida.topic.controller.feign.LogClientService;
import xmu.yida.topic.domain.Log;
import xmu.yida.topic.util.ResponseUtil;

/**
 * @author LYD
 */
@Component
public class LogClientServiceFallbackFactory implements FallbackFactory<LogClientService> {

    @Override
    public LogClientService create(Throwable throwable) {
        return new LogClientService() {
            @Override
            public Object addLog(Log log) {
                return ResponseUtil.clientShutdown("LOGSERVICE");
            }
        };
    }
}
