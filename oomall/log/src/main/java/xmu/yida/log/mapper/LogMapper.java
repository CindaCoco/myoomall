package xmu.yida.log.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.yida.log.domain.Log;

import java.util.List;

@Mapper
@Component
public interface LogMapper {

    /**
     *
     * @return log列表
     */
    public List<Log> list(Integer adminId);

    /**
     *
     * @param log
     * @return 操作结果
     */
    public boolean addLog(Log log);
}
