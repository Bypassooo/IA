package it.ztzq.service.impl;

import it.ztzq.domain.Account;
import it.ztzq.service.IAccountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("accountService")
public class AccountServiceImpl  implements IAccountService {

    @Override
    public List<Account> findAll() {
        System.out.println("service findall。。。。");
        return null;
    }

    @Override
    public void saveAccount(Account account) {
        System.out.println("业务层，保存账户。。。。");
    }
}