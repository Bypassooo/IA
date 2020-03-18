package it.ztzq.controller;

import it.ztzq.domain.Result;
import it.ztzq.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/test")
public class FrontBackController {
    @Autowired
    private ILogService logService;
    @GetMapping("/solution")
    public Set<Result> findTest(){
        System.out.println("测试前后台联通情况");
        return null;
    }
}
