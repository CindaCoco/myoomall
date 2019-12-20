package xmu.yida.topic.dao;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.yida.topic.domain.Topic;
import xmu.yida.topic.domain.TopicPO;
import xmu.yida.topic.mapper.TopicMapper;

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

    public TopicPO addTopic(TopicPO topicPO){
        topicPO.setGmtCreate(LocalDateTime.now());
        topicPO.setGmtModified(LocalDateTime.now());
        if(topicMapper.addTopic(topicPO)){
            Integer id=topicPO.getId();
            TopicPO updatedTopicPO=topicMapper.findTopicById(id);
            return updatedTopicPO;
        }else{
            return null;
        }
    }

    public Topic findTopicById(Integer id){
        TopicPO topicPO = topicMapper.findTopicById(id);
        return new Topic(topicPO);
    }

    public List<Topic> findAllTopics(Integer page,Integer limit){
        PageHelper.startPage(page,limit);
        List<TopicPO> topicPOList= topicMapper.findAllTopics();
        return topicList(topicPOList);
    }

    public boolean deleteTopicById(Integer id){
        return topicMapper.deleteTopicById(id);
    }

    public TopicPO updateTopic(TopicPO topicPO){
        topicPO.setGmtModified(LocalDateTime.now());
        if(topicMapper.updateTopic(topicPO)){
            TopicPO retTopicPO=topicMapper.findTopicById(topicPO.getId());
            return retTopicPO;
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
            topicList.add(new Topic(topicPO));
        }
        return topicList;
    }
}
