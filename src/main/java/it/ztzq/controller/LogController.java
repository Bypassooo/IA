package it.ztzq.controller;

import it.ztzq.domain.LogResult;
import it.ztzq.domain.Result;
import it.ztzq.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/log")
public class LogController {
    @Autowired
    private ILogService logService;
    @RequestMapping("/findByPid")
    public String findByPid(){
        Pageable pageable = PageRequest.of(0,100);
        //logService.findByFunctionidAndServiceidAndMethodAndMessageContains("L2620137","OTC","Ans","&_1=0,0",pageable);
        System.out.println("测试findByPid");
        return "list";
    }
    public Set<Result> findTest(){
//        Set<Result> resultReqSet = new HashSet<>();
//        Pageable pageable = PageRequest.of(0,100);
//        resultReqSet = logService.compareLog("L2620137","OTC","Ans","&_1=0,0",pageable);
//        System.out.println("测试前后台联通情况");
        return null;
    }

    public Set<LogResult> compareLog(){
        Set<LogResult> compareResult = new HashSet<>();
        Pageable pageable = PageRequest.of(0,100);
        String version = "v1";
        String functionid = "L2620137";
        String serviceid = "OTC";
        String method = "Ans";
        String checkStr = "&_1=0,0";
        String time = "2020-03-02T06:20:16.809Z";
        //首先查询数据库中是否存在对比后的记录
        compareResult = logService.compareLog(version,functionid,serviceid,method,checkStr,pageable,time);
        return compareResult;
    }
}
