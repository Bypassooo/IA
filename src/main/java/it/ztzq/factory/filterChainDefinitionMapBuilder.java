package it.ztzq.factory;

import java.util.LinkedHashMap;

public class filterChainDefinitionMapBuilder {
    public LinkedHashMap<String,String> buildFileterChainDefinitionMap(){
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        map.put("/login.jsp","anon");
        map.put("/index.jsp","anon");
//        map.put("/user.jsp","roles[user]");
//        map.put("/admin.jsp","roles[admin]");
//        map.put("/**","authc");
        return map;
    }
}
