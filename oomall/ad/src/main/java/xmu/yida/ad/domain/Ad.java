package xmu.yida.ad.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author LYD
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain=true)
@Alias("Ad")
public class Ad implements Serializable {
    private Integer id;
    /**
     * 该广告的链接
     */
    private String link;
    private String name;
    private String content;
    private String picUrl;
    /**
     * 该广告是否是默认广告
     * 0: 不是   1：是
     */
    private Boolean beDefault;
    /**
     * 该广告是否启用
     */
    private Boolean beEnabled;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private Boolean beDeleted;


}
