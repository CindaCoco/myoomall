package xmu.yida.topic.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.yida.topic.domain.Topic;
import xmu.yida.topic.domain.TopicPO;
import xmu.yida.topic.mapper.TopicMapper;
import xmu.yida.topic.util.Copyer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lyd
 */
@Repository
public class TopicDao {

    @Autowired
    TopicMapper topicMapper;

    public Topic addTopic(Topic topic){
        topic.setGmtCreate(LocalDateTime.now());
        topic.setGmtModified(LocalDateTime.now());
        if(topicMapper.addTopic(topic)){
            Integer id=topic.getId();
            TopicPO topicPO=topicMapper.findTopicById(id);
            return topic(topicPO);
        }else{
            return null;
        }
    }

    public Topic findTopicById(Integer id){
        TopicPO topicPO = topicMapper.findTopicById(id);
        return topic(topicPO);
    }

    public List<Topic> findAllTopics(Integer page,Integer limit){
        PageHelper.startPage(page,limit);
        List<TopicPO> topicPOList= topicMapper.findAllTopics();
        return topicList(topicPOList);
    }

    public boolean deleteTopicById(Integer id){
        return topicMapper.deleteTopicById(id);
    }

    public Topic updateTopic(Topic topic){
        topic.setGmtModified(LocalDateTime.now());
        if(isArgsInvalid(topic)){
            return null;
        }
        if(topicMapper.updateTopic(topic)){
            TopicPO topicPO=topicMapper.findTopicById(topic.getId());
            return topic(topicPO);
        }else{
            return null;
        }
    }

    private List<Topic> topicList(List<TopicPO> topicPOList){
        if(topicPOList==null){
            return null;
        }
        List<Topic> topicList=new ArrayList<>();
        for(TopicPO topicPO:topicPOList){
            topicList.add(topic(topicPO));
        }
        return topicList;
    }

    private Topic topic(TopicPO topicPO){
        if(topicPO==null){
            return null;
        }
        Topic topic=new Topic();
        Copyer.Copy(topicPO,topic);
        String urlList=topicPO.getPicUrlList();
        JSONArray jsonArray=JSON.parseArray(urlList);
        List<String> pictures=new ArrayList<>();
        for(int i=0;i<jsonArray.size();i++){
            pictures.add(jsonArray.get(i).toString());
        }
        topic.setPictures(pictures);
        return topic;
    }


    private boolean isArgsInvalid(TopicPO topicPO){
        if(topicPO.getBeDeleted()){
            return true;
        }
        return false;
    }
}
