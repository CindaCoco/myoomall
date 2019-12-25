package xmu.yida.topic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.yida.topic.dao.TopicDao;
import xmu.yida.topic.domain.Topic;
import xmu.yida.topic.domain.TopicPO;

import java.util.List;

/**
 * @author LYD
 */
@Service
public class TopicService {

    @Autowired
    private TopicDao topicDao;

    public TopicPO addTopic(TopicPO topicPo){
        return topicDao.addTopic(topicPo);
    }

    public Topic getTopicById(Integer id){
        return topicDao.findTopicById(id);
    }

    public List<Topic> getAllTopics(Integer page, Integer limit){
        return topicDao.findAllTopics(page,limit);
    }

    public boolean deleteTopicById(Integer id){
        return topicDao.deleteTopicById(id);
    }

    public TopicPO updateTopic(TopicPO topicPo){
        return topicDao.updateTopic(topicPo);
    }
}
