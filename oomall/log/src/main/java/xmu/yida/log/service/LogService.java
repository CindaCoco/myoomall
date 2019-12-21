package xmu.yida.log.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.yida.log.dao.LogDao;
import xmu.yida.log.domain.Log;

import java.util.List;

@Service
public class LogService {

    @Autowired
    private LogDao logDao;


    public List<Log> list(Integer page,Integer limit){
        return logDao.list(page,limit);
    }


    public boolean addLog(Log log){
        return logDao.addLog(log);
    }
}
