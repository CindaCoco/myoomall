package xmu.yida.topic.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import xmu.yida.topic.domain.Topic;
import xmu.yida.topic.domain.TopicPO;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TopicMapperTest {


    @Autowired
    TopicMapper topicMapper;

    @Test
    void addTopic() {
        TopicPO topicPo=new Topic();
        topicPo.setContent("hello topic");
        topicPo.setGmtCreate(LocalDateTime.now());
        topicPo.setGmtModified(LocalDateTime.now());
        boolean result=topicMapper.addTopic(topicPo);
        assertTrue(result);
    }

    @Test
    void findTopicById() {
        TopicPO topicPO=topicMapper.findTopicById(1);
        assertEquals("测试用的Topic",topicPO.getContent());
    }

    @Test
    void findAllTopics() {
        List<TopicPO> topicPOList=topicMapper.findAllTopics();
        assertTrue(topicPOList.size()>0);
    }

    @Test
    void deleteTopicById() {
        boolean result=topicMapper.deleteTopicById(1);
        assertTrue(result);
    }

    @Test
    void updateTopic() {
        TopicPO topicPo=new Topic();
        topicPo.setId(2);
        topicPo.setContent("hello topic");
        topicPo.setGmtCreate(LocalDateTime.now());
        topicPo.setGmtModified(LocalDateTime.now());
        boolean result=topicMapper.updateTopic(topicPo);
        assertTrue(result);
    }
}