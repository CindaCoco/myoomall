package xmu.yida.topic.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.yida.topic.domain.TopicPO;

import java.util.List;

/**
 * @Author lyd
 */
@Mapper
@Component
public interface TopicMapper {
    /**
     * 增加一个专题
     * @param topicPO 专题对象
     * @return 返回操作结果
     */
    boolean addTopic(TopicPO topicPO);

    /**
     * 通过id查找topic
     * @param id topic id
     * @return 专题
     */
    TopicPO findTopicById(Integer id);

    List<TopicPO> findAllTopics();

    /**
     * 通过id删除专题
     * @param id 专题id
     * @return 操作结果
     */
    boolean deleteTopicById(Integer id);

    /**
     * 修改专题
     * @param topicPO 要修改的专题
     * @return 操作结果
     */
    boolean updateTopic(TopicPO topicPO);
}
