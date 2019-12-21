package xmu.yida.log.dao;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.yida.log.domain.Log;
import xmu.yida.log.mapper.LogMapper;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class LogDao {

    @Autowired
    private LogMapper logMapper;

    public List<Log> list(Integer page,Integer limit){
        PageHelper.startPage(page,limit);
        return logMapper.list();
    }


    public boolean addLog(Log log){
        log.setGmtCreate(LocalDateTime.now());
        log.setGmtModified(LocalDateTime.now());
        return logMapper.addLog(log);
    }
}
