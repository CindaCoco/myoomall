package xmu.yida.log.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.yida.log.domain.Log;
import xmu.yida.log.service.LogService;
import xmu.yida.log.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;


@RestController
public class LogController {

    @Autowired
    private LogService logService;


    private Integer getUserId(HttpServletRequest request) {
        String userIdStr = request.getHeader("userId");
        if (userIdStr == null) {
            return null;
        }
        return Integer.valueOf(userIdStr);
    }

    @GetMapping("/logs")
    public Object list(@RequestParam(name = "page",defaultValue = "1")Integer page,
                       @RequestParam(name = "limit",defaultValue = "10") Integer limit,
                       @RequestParam(name = "adminId",required = false)Integer adminId,
                       HttpServletRequest request){
        Integer loginAdminId=getUserId(request);
        if(loginAdminId==null){
            return ResponseUtil.fail(668,"管理员未登录");
        }

        if(page<=0||limit<=0){
            return ResponseUtil.fail(901,"查看日志失败");
        }

        return ResponseUtil.ok(logService.list(page,limit,adminId));
    }


    @PostMapping("/log")
    public Object addLog(@RequestBody Log log){
        if(log==null){
            return ResponseUtil.fail();
        }
        if(logService.addLog(log)){
            return ResponseUtil.ok();
        }else {
            return ResponseUtil.fail();
        }
    }
}
