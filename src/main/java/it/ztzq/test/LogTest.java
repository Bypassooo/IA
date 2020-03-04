package it.ztzq.test;

import it.ztzq.domain.Log;
import it.ztzq.repositories.LogRepository;
import it.ztzq.repositories.RmsgRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class LogTest {
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private RmsgRepository rmsgRepository;

    @Test
    public void findAll() throws Exception{
        Iterable<Log> logs = logRepository.findAll();
        logs.forEach(a-> System.out.println(a));
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
    public void test() throws Exception{
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();
        Set<String> set3 = new HashSet<>();
        set1.add("1");
        set1.add("2");
        set2.add("1");
        set2.add("2");
        set2.add("3");

        set3.addAll(set1);
        System.out.println(set3);
        set3.retainAll(set2);
        System.out.println(set3);

        }

}
