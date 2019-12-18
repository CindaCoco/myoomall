package xmu.yida.topic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: 数据库与对象模型标准组
 * @Description: 专题信息
 * @Date: Created in 16:00 2019/11/29
 * @Modified By:
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain=true)
@Alias("TopicPO")
public class TopicPO implements Serializable {
    private Integer id;
    private String picUrlList;
    private String content;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private Boolean beDeleted;

    public TopicPO(String picUrlList, String content) {
        this.picUrlList = picUrlList;
        this.content = content;
    }
}
