package it.ztzq.controller;

import it.ztzq.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @RequestMapping("/findAll")
    public String findAll(){
        System.out.println("表现层：查询所有账户。。。。");

        //调用service方法
        accountService.findAll();

        return "list";
    }
}

