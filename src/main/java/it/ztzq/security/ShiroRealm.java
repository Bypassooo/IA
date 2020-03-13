package it.ztzq.security;

import javafx.beans.binding.ObjectExpression;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.Set;

public class ShiroRealm extends AuthorizingRealm {
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("doGetAuthenticationInfo "+authenticationToken);
        //1. 把AuthenticationToken转换为UsernamePasswordToken
        UsernamePasswordToken upToken = (UsernamePasswordToken)authenticationToken;
        //2. 从UsernamePasswordToken中获取username
        String username = upToken.getUsername();
        //3. 调用数据库的方法，从数据库中查询username对应的用户记录
        System.out.println("从数据库中获取username: " + username + " 所对应的信息");
        //4. 若用户不存在，则可以抛出UnknownAccountException异常
        if("unknown".equals(username)){
            throw new UnknownAccountException("用户不存在");
        }
        //5. 根据用户信息的情况，决定是否需要抛出其他的AuthenticationException异常
        if("monster".equals(username)){
            throw new LockedAccountException("账户已被锁定");
        }
        //6. 根据用户情况来来构建AuthenticationInfo对象并返回
        // 认证的实体信息
        Object principal = username;
        // 从数据库获得的密码
        Object credentials = "fc1709d0a95a6be30bc5926fdb7f22f4";
        //当前realm对象的name，调用父类的getName即可
        String realName = getName();
        // 盐值
        Object credentialsSalt = ByteSource.Util.bytes(username);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,credentials,realName);
        return info;

        //密码比对是通过AuthenticatingRealm的credentialsMatcher属性来进行密码的对比的
    }
    //  授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 1. 从principalCollection中获取登陆用户的信息
        Object principal = principalCollection.getPrimaryPrincipal();
        // 2. 利用登陆用户的信息来获取当前用户的角色或者权限（登陆用户信息中已经包含或者需要查询数据库)
        Set<String> roles = new HashSet<>();
        ///////使用user登陆只有user角色，使用admin登陆用户user和admin两个角色
        roles.add("user");
        if("admin".equals(principal)){
            roles.add("admin");
        }
        // 3. 创建SimpleAuthorizationInfo,并设置其roles属性
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        // 4. 返回SimpleAuthorizationInfo对象
        System.out.println("doGetAuthorizationInfo");
        return info;
    }

    public static void main(String[] args){
        String hashAlgorithmName = "MD5";
        Object credentials = "123456";
        Object salt = null;
        //使用MD5盐值加密，避免同样密码存储相同问题
        // 在doGet方法返回值创建simpleAuthentication对象的时候，需要使用SimpleAuthenticationInfo(principal,credentials,credentialsSalt,realmName)构造器
        // 使用 ByteSource.Util.bytes()来计算盐值
        // 盐值需要唯一
        // 使用new SimpleHash(hashAlgorithmName,credentials,salt,hashIterations)来计算盐值加密后的密码的值。
        int hashIterations = 1024;
        Object result = new SimpleHash(hashAlgorithmName,credentials,salt,hashIterations);
        //*****result为加了盐值之后加密的密码，也就是数据库中存放的密码，在doGet中创建返回信息时需要用到这个加密的密码
        System.out.println(result);
    }
}
