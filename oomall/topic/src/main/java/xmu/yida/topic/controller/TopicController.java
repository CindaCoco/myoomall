package xmu.yida.topic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import xmu.yida.topic.controller.feign.AdClientService;
import xmu.yida.topic.domain.Topic;
import xmu.yida.topic.service.TopicService;
import xmu.yida.topic.util.ResponseUtil;


/**
 * @author lyd
 */

@RestController
public class TopicController {

    private static final String ADSERVICEURL="http://ADSERVICE";

    @Autowired
    private AdClientService adClientService;

    @Autowired
    TopicService topicService;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    RestTemplate restTemplate;
    /**
     * 专题列表
     *
     * @param page 分页页数
     * @param limit 分页大小
     * @return 专题列表
     */
    @GetMapping("/topics")
    public Object list(@RequestParam(defaultValue = "1",name = "page") Integer page,
                       @RequestParam(defaultValue = "10",name = "limit") Integer limit){
        return ResponseUtil.ok(topicService.getAllTopics(page,limit));
    }

    /**
     * 专题详情
     *
     * @param id 专题ID
     * @return 专题详情
     */
    @GetMapping("/topics/{id}")
    public Object detail(@PathVariable Integer id){
        Topic topic=topicService.getTopicById(id);
        if(topic==null){
            return ResponseUtil.notFound();
        }else{
            return ResponseUtil.ok(topic);
        }
    }

    @PostMapping("/topics")
    public Object create(@RequestBody Topic topic) {
        Topic retTopic=topicService.addTopic(topic);
        if(retTopic==null){
            return ResponseUtil.createFailed();
        }else{
            return ResponseUtil.ok(topic);
        }
    }
    @PutMapping("/topics/{id}")
    public Object update(@RequestBody Topic topic,@PathVariable Integer id) {
        if(topic.getGmtCreate()==null&&topic.getGmtModified()==null){
            topic.setId(id);
            Topic retTopic=topicService.updateTopic(topic);
            if(retTopic==null){
                return ResponseUtil.updatedDataFailed();
            }else{
                return ResponseUtil.ok(topic);
            }
        }else{
            return ResponseUtil.badArgument();
        }
    }

    @DeleteMapping("/topics/{id}")
    public Object delete(@PathVariable Integer id) {
        boolean result=topicService.deleteTopicById(id);
        if(result){
            return ResponseUtil.ok();
        }else{
            return ResponseUtil.fail();
        }
    }

    @GetMapping("/test")
    public Object hello(){
        return adClientService.getAds();
    }


}
