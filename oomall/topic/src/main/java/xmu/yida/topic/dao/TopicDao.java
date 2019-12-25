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

    public TopicPO addTopic(TopicPO topicPo){
        topicPo.setGmtCreate(LocalDateTime.now());
        topicPo.setGmtModified(LocalDateTime.now());
        if(topicMapper.addTopic(topicPo)){
            Integer id=topicPo.getId();
            TopicPO updatedTopicPo=topicMapper.findTopicById(id);
            return updatedTopicPo;
        }else{
            return null;
        }
    }

    public Topic findTopicById(Integer id){
        TopicPO topicPo = topicMapper.findTopicById(id);
        if(topicPo==null){
            return null;
        }
        return new Topic(topicPo);
    }

    public List<Topic> findAllTopics(Integer page,Integer limit){
        PageHelper.startPage(page,limit);
        List<TopicPO> topicPoList= topicMapper.findAllTopics();
        return topicList(topicPoList);
    }

    public boolean deleteTopicById(Integer id){
        TopicPO topicPo=topicMapper.findTopicById(id);
        if(topicPo==null){
            return false;
        }
        if(topicPo.getBeDeleted()){
            return false;
        }
        return topicMapper.deleteTopicById(id);
    }

    public TopicPO updateTopic(TopicPO topicPo){
        topicPo.setGmtModified(LocalDateTime.now());
        if(topicMapper.updateTopic(topicPo)){
            TopicPO retTopicPo=topicMapper.findTopicById(topicPo.getId());
            return retTopicPo;
        }else{
            return null;
        }
    }

    private List<Topic> topicList(List<TopicPO> topicPoList){
        if(topicPoList==null){
            return null;
        }
        List<Topic> topicList=new ArrayList<>();
        for(TopicPO topicPo:topicPoList){
            topicList.add(new Topic(topicPo));
        }
        return topicList;
    }
}
