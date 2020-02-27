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
//    @Autowired
//    private ElasticsearchUtil es;

    @RequestMapping("/findAll")
    public String findAll(){
        System.out.println("controller findall ");

        //调用service方法
        accountService.findAll();

     //   es.estest();

        return "list";
    }
}

