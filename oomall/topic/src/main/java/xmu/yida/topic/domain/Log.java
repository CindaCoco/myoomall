package xmu.yida.topic.domain;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: 数据库与对象模型标准组
 * @Description:日志信息
 * @Data:Created in 14:50 2019/12/11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Log implements Serializable {
    private Integer id;
    /**
     * 进行该操作的管理员ID
     */
    private Integer adminId;
    /**
     * 操作者的IP地址
     */
    private String ip;
    /**
     * 操作的类型
     * 0 查询，1 插入，2修改，3删除(逻辑删除)
     */
    private Integer type;
    /**
     * 操作的动作
     */
    private String actions;
    /**
     * 操作的状态，0表示操作失败，1表示操作成功
     */
    private Integer statusCode;
    /**
     * 操作对象的ID
     */
    private Integer actionId;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    public Log(Integer adminId, String ip, Integer type, String actions, Integer statusCode, Integer actionId) {
        this.adminId = adminId;
        this.ip = ip;
        this.type = type;
        this.actions = actions;
        this.statusCode = statusCode;
        this.actionId = actionId;
    }
}
