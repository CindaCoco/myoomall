package xmu.yida.topic.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
    public Topic(TopicPO topicPO){
        this.setId(topicPO.getId());
        this.setContent(topicPO.getContent());
        this.setPicUrlList(topicPO.getPicUrlList());
        this.setGmtCreate(topicPO.getGmtCreate());
        this.setGmtModified(topicPO.getGmtModified());
        this.setBeDeleted(topicPO.getBeDeleted());
        String picUrlList=this.getPicUrlList();
        if(picUrlList!=null){
            JSONArray jsonArray= JSON.parseArray(picUrlList);
            List<String> pictures=new ArrayList<>();
            for(int i=0;i<jsonArray.size();i++){
                pictures.add(jsonArray.get(i).toString());
            }
            this.setPictures(pictures);
        }
    }
}
