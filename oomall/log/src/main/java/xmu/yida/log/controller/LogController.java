package xmu.yida.log.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.yida.log.domain.Log;
import xmu.yida.log.service.LogService;
import xmu.yida.log.util.ResponseUtil;


@RestController
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("/logs")
    public Object list(@RequestParam(name = "page",defaultValue = "1")Integer page,
                       @RequestParam(name = "limit",defaultValue = "10") Integer limit,
                       @RequestParam(name = "adminId")Integer adminId){
        return ResponseUtil.ok(logService.list(page,limit));
    }


    @PostMapping("/log")
    public Object addLog(@RequestBody Log log){
        if(logService.addLog(log)){
            return ResponseUtil.ok();
        }else {
            return ResponseUtil.fail();
        }
    }
}
