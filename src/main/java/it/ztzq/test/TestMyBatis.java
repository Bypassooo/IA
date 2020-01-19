package it.ztzq.test;

import it.ztzq.dao.IAccountDao;
import it.ztzq.domain.Account;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class TestMyBatis {

    @Test
    public void run() throws  Exception{
        //加载配置文件
        InputStream in =  Resources.getResourceAsStream("SqlMapConfig.xml");
        //创建SqlSessionFactory对象
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //创建SqlSession对象
        SqlSession session = factory.openSession();
        //获取代理对象
        IAccountDao dao = session.getMapper(IAccountDao.class);
        //调用dao方法
        List<Account> list = dao.findAll();
        for(Account account : list){
            System.out.println(account);
        }
        //关闭资源
        session.close();
        in.close();
    }
}
