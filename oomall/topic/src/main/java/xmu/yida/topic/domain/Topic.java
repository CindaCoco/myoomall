package xmu.yida.topic.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LYD
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Topic extends TopicPO implements Serializable {
    private List<String> pictures;
    public Topic(TopicPO topicPo){
        this.setId(topicPo.getId());
        this.setContent(topicPo.getContent());
        this.setPicUrlList(topicPo.getPicUrlList());
        this.setGmtCreate(topicPo.getGmtCreate());
        this.setGmtModified(topicPo.getGmtModified());
        this.setBeDeleted(topicPo.getBeDeleted());
        String picUrlList=this.getPicUrlList();
        if(picUrlList!=null){
            JSONObject jsonObject=JSON.parseObject(picUrlList);
            String pictures=jsonObject.getString("pictures");
            this.setPictures(JSON.parseArray(pictures,String.class));
        }
    }
}
