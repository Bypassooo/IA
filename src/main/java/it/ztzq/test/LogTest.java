package it.ztzq.test;

import it.ztzq.domain.Log;
import it.ztzq.domain.Message;
import it.ztzq.repositories.LogRepository;
import it.ztzq.service.impl.LogServiceImpl;
import org.elasticsearch.common.util.set.Sets;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.InputStream;
import java.util.*;

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
        Map<String,String> map1 = new HashMap<>();
        Map<String,String> map2 = new HashMap<>();
        Set<String> keyInter = new HashSet<String>();
        map1.put("1","a");
        map1.put("2","b");
        map1.put("3","c");

        map2.put("1","a");
        map2.put("2","b");
        map2.put("4","d");
        map2.put("5","e");

        Set<String> mapKey1 = map1.keySet();
        Set<String> mapKey2 = map2.keySet();

        keyInter.addAll(mapKey1);
        keyInter.retainAll(mapKey2);

        System.out.println(mapKey1);
        System.out.println(mapKey2);
        System.out.println(keyInter);



        }

}
