package it.ztzq.controller;

import it.ztzq.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/log")
public class LogController {
    @Autowired
    private ILogService logService;
    @RequestMapping("/findByPid")
    public String findByPid(){
        logService.findByPid("1c1c-198c");
        System.out.println("测试findByPid");
        return "list";
    }
}
