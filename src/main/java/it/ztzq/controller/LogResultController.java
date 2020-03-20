package it.ztzq.controller;

import it.ztzq.domain.LogResult;
import it.ztzq.service.ILogResultService;
import it.ztzq.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/logresult")
public class LogResultController {
    @Autowired
    private ILogResultService logResultService;
    @Autowired
    private ILogService logService;

    @RequestMapping("/createindex")
    public void createIndex(){
        logResultService.createIndex();
    }

    @RequestMapping("/page/{page}/{size}")
    public Page<LogResult> comparePageTest(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Set<LogResult> compareResult = new HashSet<>();
        Pageable pageable = PageRequest.of(2,1);
        String version = "v1";
        String functionid = "L2620137";
        String serviceid = "OTC";
        String method = "Ans";
        String checkStr = "&_1=0,0";
        String time = "2019-12-30T13:58:31.000Z";
        //首先查询数据库中是否存在对比后的记录
        Page<LogResult> logResultList = logResultService.getLogResultsByVersion(version,0,1);
        boolean existlog = false;
        System.out.println(logResultList.getTotalPages());
        if(logResultList.getTotalPages() > 0){
            existlog = true;
        }
        if(!existlog){
            //先比较，然后将比较结果存入数据库，再查询到结果集返给前台
            compareResult = logService.compareLog(version,functionid,serviceid,method,checkStr,pageable,time);
            for(LogResult logResult:compareResult){
                logResultService.addDocument(logResult);
            }
        }
        logResultList = logResultService.getLogResultsByVersion(version,page,size);
        return logResultList;
    }
}

