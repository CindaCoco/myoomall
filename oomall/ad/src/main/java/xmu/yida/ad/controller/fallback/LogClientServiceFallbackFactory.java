package xmu.yida.ad.controller.fallback;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import xmu.yida.ad.controller.feign.LogClientService;
import xmu.yida.ad.domain.Log;
import xmu.yida.ad.util.ResponseUtil;

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
