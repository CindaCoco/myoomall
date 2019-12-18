package xmu.yida.topic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.yida.topic.dao.TopicDao;
import xmu.yida.topic.domain.Topic;

import java.util.List;

@Service
public class TopicService {

    @Autowired
    private TopicDao topicDao;

    public Topic addTopic(Topic topic){
        return topicDao.addTopic(topic);
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

    public Topic updateTopic(Topic topic){
        return topicDao.updateTopic(topic);
    }
}
