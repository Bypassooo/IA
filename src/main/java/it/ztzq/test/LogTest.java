package it.ztzq.test;

import it.ztzq.domain.Log;
import it.ztzq.repositories.LogRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class LogTest {
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void findAll() throws Exception{
        Iterable<Log> logs = logRepository.findAll();
        logs.forEach(a-> System.out.println(a));
    }

    @Test
    public void findBynodeId() throws Exception{
        List<Log> logs = logRepository.findByNodeId("9821");
        logs.stream().forEach(log -> System.out.println(log));
    }

    @Test
    public void findByBuf() throws Exception{
        List<Log> logs = logRepository.findByBuf("01300000000000000000309821098210000098F2B3CDB850KCXP00 GV2gODkBbGg");
        logs.stream().forEach(log -> System.out.println(log));
    }

    @Test
    public void findByMethod() throws Exception{
        List<Log> logs = logRepository.findByMethod("Ans");
        logs.stream().forEach(log -> System.out.println(log));
    }

    @Test
    public void findByPid() throws Exception{
        List<Log> logs = logRepository.findByPid("1c1c-198c");
        logs.stream().forEach(log -> System.out.println(log));
    }

    @Test
    public void findByMsgId() throws Exception{
        List<Log> logs = logRepository.findByMsgId("66800121015E2EAB21F81431");
        logs.stream().forEach(log -> System.out.println(log));
    }

}
