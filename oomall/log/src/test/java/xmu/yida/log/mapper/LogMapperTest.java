package xmu.yida.log.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import xmu.yida.log.domain.Log;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LogMapperTest {

    @Autowired
    LogMapper logMapper;

    @Test
    void list() {
        List<Log> logList=logMapper.list(2);
        assertTrue(logList.size()>0);
    }

    @Test
    void addLog() {
        Log log=new Log();
        log.setIp("666.666.666.666");
        boolean result=logMapper.addLog(log);
        assertTrue(result);
    }
}