package it.ztzq.controller;

import it.ztzq.service.IShiroService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/shiro")
public class ShiroController {
    @Autowired
    private IShiroService shiroService;
    @RequestMapping("/testShiroAnnotation")
    public String testShiroAnnotation(){
        shiroService.testMethod();
        return "redirect:/final.jsp";
    }
    @RequestMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password){
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isAuthenticated()){
            UsernamePasswordToken token = new UsernamePasswordToken(username,password);
            token.setRememberMe(true);
            try{
                currentUser.login(token);
            }catch(AuthorizationException e){
                System.out.println("登录失败"+e.getMessage());
            }
        }
        return "redirect:/index.jsp";
    }
}

