package xmu.yida.log.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.yida.log.domain.Log;

import java.util.List;

/**
 * @author LYD
 */
@Mapper
@Component
public interface LogMapper {

    /** 通过管理员id查看对应的日志
     * @param adminId 管理员id
     * @return log列表
     */
    List<Log> list(Integer adminId);

    /**
     *  添加一条日志
     * @param log 添加的日志
     * @return 操作结果
     */
    boolean addLog(Log log);
}
