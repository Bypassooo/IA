package it.ztzq.test;

import it.ztzq.domain.Log;
import it.ztzq.domain.Message;
import it.ztzq.repositories.LogRepository;
import it.ztzq.service.impl.LogServiceImpl;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
    public  void testStr() throws Exception{
        InputStream in = LogServiceImpl.class.getClassLoader().getResourceAsStream("trans/KESB_To_KFMS.xml");
        SAXBuilder sb = new SAXBuilder();
        Document document = sb.build(in);
            //获取根节点
        Element root = document.getRootElement();
        List<Element> childList = root.getChildren();
        List<Message> messageList =  new ArrayList<Message>();

        for(Element e: childList){
            Message message = new Message();
            if(e.getName() == "dict") {
                continue;
            }
            else {
                //将每个message节点放入message中
                message.setTitle(e.getAttributeValue("title"));
                message.setDst(e.getAttributeValue("dst"));
                message.setSrc(e.getAttributeValue("src"));
                Element inputElement = e.getChild("input");
                //获取field节点列表
                List<Element> fieldList = inputElement.getChildren();
                Map<String,String> fieldMap = new HashMap<String,String>();
                for(Element field :fieldList){
                    fieldMap.put(field.getAttributeValue("dst"),field.getAttributeValue("src"));
                }
                message.setFieldMap(fieldMap);
            }
            messageList.add(message);
        }
        //检查数据是否都存入List中
        for(Message m:messageList){
            System.out.println(m.getTitle());
            System.out.println(m.getDst());
            System.out.println(m.getSrc());
            for(Map.Entry<String,String> entry:m.getFieldMap().entrySet()){
                System.out.println("dst="+entry.getKey()+",src="+entry.getValue());
            }
        }
    }

    @Test
    public void Testfunction()throws Exception{
        //List<Log> logs = logRepository.findByOffsetAndNodeIdAndMethodAndMessageContains(100L,"9821","Ans","&_1=0,0");
        List<Log> logs =logRepository.findByFunctionidAndServiceidAndMethodAndMessageContains("L2620137","OTC","Ans","&_1=0,0");
        System.out.println(logs);
    }
}
