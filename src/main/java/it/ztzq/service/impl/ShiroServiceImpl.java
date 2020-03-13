package it.ztzq.service.impl;

import it.ztzq.service.IShiroService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Service;

public class ShiroServiceImpl implements IShiroService {
    @RequiresRoles({"admin"})
    public  void testMethod(){
        System.out.println("test method");
    }
}
