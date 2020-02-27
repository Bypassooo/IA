package it.ztzq.service.impl;

import it.ztzq.domain.Log;
import it.ztzq.repositories.LogRepository;
import it.ztzq.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("logService")
public class LogServiceImpl implements ILogService {

    @Autowired
    private LogRepository logRepository;
    @Override
    public void findByPid(String pid){
        List<Log> logs = logRepository.findByPid("1c1c-198c");
        logs.stream().forEach(log -> System.out.println(log));
    }
}
