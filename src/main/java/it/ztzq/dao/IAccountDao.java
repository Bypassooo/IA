package it.ztzq.dao;

import it.ztzq.domain.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IAccountDao {

    @Select("select * from account")
    public List<Account> findAll();

    @Insert("insert into account(name,money) values(#{name},#{money})")
    public void saveAccount(Account account);
}
