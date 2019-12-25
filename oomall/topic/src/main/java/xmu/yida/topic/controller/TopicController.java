package xmu.yida.topic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
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


    @Autowired
    private LogClientService logClientService;

    @Autowired
    TopicService topicService;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    RestTemplate restTemplate;

    private final static Integer SELECT=0;
    private final static Integer INSERT=1;
    private final static Integer UPDATE=2;
    private final static Integer DELETE=3;

    @GetMapping(value = "/admin/topics",produces = "application/json;charset=utf-8")
    public Object adminGetTopicList(@RequestParam(defaultValue = "1",name="page") Integer page,
                            @RequestParam(defaultValue = "10",name = "limit") Integer limit,
                            HttpServletRequest request){
        Integer adminId=getUserId(request);
        if(adminId==null){
            return ResponseUtil.fail(668,"管理员未登录");
        }
        if(page<=0||limit<=0){
            return ResponseUtil.fail(580,"参数不合法");
        }
        String ip=request.getHeader("ip");
        Object retObj=topicService.getAllTopics(page,limit);
        if(retObj!=null){
            createLog(adminId,ip,SELECT,"查询话题列表",1,null);
            return ResponseUtil.ok(retObj);
        }else{
            createLog(adminId,ip,SELECT,"查询话题列表",0,null);
            return ResponseUtil.fail(654,"话题查看失败");
        }
    }
    /**
     * 专题列表
     *
     * @param page 分页页数
     * @param limit 分页大小
     * @return 专题列表
     */
    @GetMapping(value = "/topics",produces = "application/json;charset=utf-8")
    public Object list(@RequestParam(defaultValue = "1",name = "page") Integer page,
                       @RequestParam(defaultValue = "10",name = "limit") Integer limit){
        if(page<=0||limit<=0){
            return ResponseUtil.fail(580,"参数不合法");
        }
        Object retObj=topicService.getAllTopics(page,limit);
        if(retObj!=null){
            return ResponseUtil.ok(retObj);
        }else{
            return ResponseUtil.fail(654,"话题查看失败");
        }
    }


    @GetMapping(value = "/admin/topics/{id}",produces = "application/json;charset=utf-8")
    public Object adminDetail(@PathVariable Integer id,HttpServletRequest request){
        Integer adminId=getUserId(request);
        if(adminId==null){
            return ResponseUtil.fail(668,"管理员未登录");
        }
        String ip=request.getHeader("ip");
        if(id==null||id<=0){
            return ResponseUtil.fail(580,"参数错误");
        }
        Topic topic=topicService.getTopicById(id);
        if(topic==null){
            createLog(adminId,ip,SELECT,"查看话题",0,id);
            return ResponseUtil.fail(650,"该话题是无效话题");
        }else{
            createLog(adminId,ip,SELECT,"查看话题",1,id);
            return ResponseUtil.ok(topic);
        }
    }

    /**
     * 专题详情
     *
     * @param id 专题ID
     * @return 专题详情
     */
    @GetMapping(value = "/topics/{id}",produces = "application/json;charset=utf-8")
    public Object detail(@PathVariable Integer id){
        if(id==null||id<=0){
            return ResponseUtil.fail(580,"参数不合法");
        }
        Topic topic=topicService.getTopicById(id);
        if(topic==null){
            return ResponseUtil.fail(650,"该话题是无效话题");
        }else{
            return ResponseUtil.ok(topic);
        }
    }

    @PostMapping(value = "/topics",produces = "application/json;charset=utf-8")
    public Object create(@RequestBody TopicPO topicPo,HttpServletRequest request) {
        Integer adminId=getUserId(request);
        if(adminId==null){
            return ResponseUtil.fail(668,"管理员未登录");
        }
        String ip=request.getHeader("ip");
        TopicPO retTopic=topicService.addTopic(topicPo);
        if(retTopic==null){
            createLog(adminId,ip,INSERT,"添加话题",0,null);
            return ResponseUtil.fail(652,"话题添加失败");
        }else{
            createLog(adminId,ip,INSERT,"添加话题",1,retTopic.getId());
            return ResponseUtil.ok(topicPo);
        }
    }
    @PutMapping(value = "/topics/{id}",produces = "application/json;charset=utf-8")
    public Object update(@RequestBody TopicPO topicPo,@PathVariable Integer id,HttpServletRequest request) {
        Integer adminId=getUserId(request);
        if(adminId==null){
            return ResponseUtil.fail(668,"管理员未登录");
        }
        String ip=request.getHeader("ip");
        if(id==null||id<=0){
            return ResponseUtil.fail(580,"参数不合法");
        }
        topicPo.setId(id);
        TopicPO retTopic=topicService.updateTopic(topicPo);
        if(retTopic==null){
            createLog(adminId,ip,UPDATE,"更新话题",0,id);
            return ResponseUtil.fail(651,"话题更新失败");
        }else{
            createLog(adminId,ip,UPDATE,"更新话题",1,id);
            return ResponseUtil.ok(retTopic);
        }
    }

    @DeleteMapping(value = "/topics/{id}",produces = "application/json;charset=utf-8")
    public Object delete(@PathVariable Integer id,HttpServletRequest request) {
        Integer adminId=getUserId(request);
        if(adminId==null){
            return ResponseUtil.fail(668,"管理员未登录");
        }
        if(id==null||id<=0){
            return ResponseUtil.fail(580,"参数不合法");
        }
        String ip=request.getHeader("ip");
        boolean result=topicService.deleteTopicById(id);
        if(result){
            createLog(adminId,ip,DELETE,"删除话题",1,id);
            return ResponseUtil.ok();
        }else{
            createLog(adminId,ip,DELETE,"删除话题",0,id);
            return ResponseUtil.fail(653,"话题删除失败");
        }
    }

    private void createLog(Integer adminId,String ip,Integer type,String actions,
                           Integer statusCode,Integer actionId){
        Log log=new Log();
        log.setAdminId(adminId);
        log.setIp(ip);
        log.setType(type);
        log.setActions(actions);
        log.setStatusCode(statusCode);
        log.setActionId(actionId);
        logClientService.addLog(log);
    }

    private Integer getUserId(HttpServletRequest request){
        String userId=request.getHeader("userid");
        if(userId==null){
            return null;
        }
        return Integer.valueOf(userId);
    }
}
