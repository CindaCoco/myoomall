package xmu.yida.topic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import xmu.yida.topic.controller.feign.AdClientService;
import xmu.yida.topic.controller.feign.LogClientService;
import xmu.yida.topic.domain.Log;
import xmu.yida.topic.domain.Topic;
import xmu.yida.topic.domain.TopicPO;
import xmu.yida.topic.service.TopicService;
import xmu.yida.topic.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;


/**
 * @author lyd
 */

@RestController
public class TopicController {

    private static final String ADSERVICEURL="http://ADSERVICE";

    @Autowired
    private AdClientService adClientService;

    @Autowired
    private LogClientService logClientService;

    @Autowired
    TopicService topicService;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    RestTemplate restTemplate;


    @GetMapping("/admin/topics")
    public Object adminlist(@RequestParam(defaultValue = "1",name="page") Integer page,
                            @RequestParam(defaultValue = "10",name = "limit") Integer limit,
                            HttpServletRequest request){
        Object retObj=topicService.getAllTopics(page,limit);
        if(retObj!=null){
            Log log=new Log(request.getIntHeader("userId"),
                    request.getHeader("ip"),0,"查询专题列表",1,null);
            logClientService.addLog(log);
            return ResponseUtil.ok(retObj);
        }else{
            Log log=new Log(request.getIntHeader("userId"),
                    request.getHeader("ip"),0,"查询专题列表",0,null);
            logClientService.addLog(log);
            return ResponseUtil.topicGetFailed();
        }
    }
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
        Object retObj=topicService.getAllTopics(page,limit);
        if(retObj!=null){
            return ResponseUtil.ok(retObj);
        }else{
            return ResponseUtil.topicGetFailed();
        }
    }


    @GetMapping("/admin/topics/{id}")
    public Object adminDetail(@PathVariable Integer id,HttpServletRequest request){
        Topic topic=topicService.getTopicById(id);
        if(topic==null){
            Log log=new Log(request.getIntHeader("userId"),
                    request.getHeader("ip"),0,"查询专题详情",0,id);
            logClientService.addLog(log);
            return ResponseUtil.invalidTopic();
        }else{
            Log log=new Log(request.getIntHeader("userId"),
                    request.getHeader("ip"),0,"查询专题详情",1,id);
            logClientService.addLog(log);
            return ResponseUtil.ok(topic);
        }
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
            return ResponseUtil.invalidTopic();
        }else{
            return ResponseUtil.ok(topic);
        }
    }

    @PostMapping("/topics")
    public Object create(@RequestBody TopicPO topicPO,HttpServletRequest request) {
        TopicPO retTopic=topicService.addTopic(topicPO);
        if(retTopic==null){
            Log log=new Log(request.getIntHeader("userId"),
                    request.getHeader("ip"),1,"插入专题",0,null);
            logClientService.addLog(log);
            return ResponseUtil.topicAddFailed();
        }else{
            Log log=new Log(request.getIntHeader("userId"),
                    request.getHeader("ip"),1,"插入专题",1,null);
            logClientService.addLog(log);
            return ResponseUtil.ok(topicPO);
        }
    }
    @PutMapping("/topics/{id}")
    public Object update(@RequestBody TopicPO topicPO,@PathVariable Integer id,HttpServletRequest request) {
            topicPO.setId(id);
            TopicPO retTopic=topicService.updateTopic(topicPO);
            if(retTopic==null){
                Log log=new Log(request.getIntHeader("userId"),
                        request.getHeader("ip"),2,"修改专题",0,id);
                logClientService.addLog(log);
                return ResponseUtil.topicUpdateFailed();
            }else{
                Log log=new Log(request.getIntHeader("userId"),
                        request.getHeader("ip"),2,"插入专题",1,id);
                logClientService.addLog(log);
                return ResponseUtil.ok(topicPO);
            }
    }

    @DeleteMapping("/topics/{id}")
    public Object delete(@PathVariable Integer id,HttpServletRequest request) {
        boolean result=topicService.deleteTopicById(id);
        if(result){
            Log log=new Log(request.getIntHeader("userId"),
                    request.getHeader("ip"),3,"删除专题",1,id);
            logClientService.addLog(log);
            return ResponseUtil.ok();
        }else{
            return ResponseUtil.topicDeleteFailed();
        }
    }

    @GetMapping("/test")
    public Object hello(){
        return adClientService.getAds();
    }


    /**
     * 写日志，返回正确或者错误码
     * @param log 日志
     * @param retObj 返回码
     * @return 返回码
     */
    public Object returnResult(Log log, Object retObj)
    {
        try {
            logClientService.addLog(log);
            return retObj;
        } catch (Exception e) {
            System.out.println("写日志失败");
            return retObj;
        }
    }
}
