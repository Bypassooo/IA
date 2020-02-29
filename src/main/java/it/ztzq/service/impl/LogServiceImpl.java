package it.ztzq.service.impl;

import it.ztzq.domain.Log;
import it.ztzq.repositories.LogRepository;
import it.ztzq.service.ILogService;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resources;
import java.io.File;
import java.io.InputStream;
import java.util.*;

@Service("logService")
public class LogServiceImpl implements ILogService {

    @Autowired
    private LogRepository logRepository;
    @Override
    public void findByOffsetAndNodeIdAndMethodAndMessageContains(Long offSet, String nodeId, String method, String checkStr){
        List<Log> logs = logRepository.findByOffsetAndNodeIdAndMethodAndMessageContains(offSet, nodeId, method, checkStr);
        //一个功能号只有一条应答（确认是否只有一条应答)
        Optional<Log> optional = logRepository.findByMsgIdAndMethod(logs.get(0).getMsgId(),"Req");
        String msgMessage = optional.get().getMessage();
        //buf之后的字符串
        String bufStr = msgMessage.substring(msgMessage.indexOf("Buf"));
        //将buf中需要比对的字符串截取出来
        String compareStr = bufStr.substring(bufStr.indexOf("_ENDIAN=0&")+10,bufStr.indexOf("&_KesbServiceIDVersion"));
        List<String> strList = new ArrayList<String>();
        Map<String,String> strMap = new HashMap<String,String>();
        for(String str:compareStr.split("&")){
            strList.add(str);
        }

        for(String out :strList){
            String[] buff = {"",""};
            buff = out.split("=");
            strMap.put(buff[0],buff.length==2?buff[1]:"");
        }
        for(Map.Entry<String,String> entry:strMap.entrySet()){
            System.out.println(entry.getKey()+"="+entry.getValue());
        }

        /////读取xml配置文件
        try {
            InputStream in = LogServiceImpl.class.getClassLoader().getResourceAsStream("KESB_To_KFMS.xml");
            SAXBuilder sb = new SAXBuilder();
            Document document = sb.build(in);
            //获取根节点
            Element root = document.getRootElement();
            List<Element> childList = root.getChildren();
            for(Element e: childList){
                if(e.getName() == "dict")
                {
                    continue;
                }
                else
                {
                    System.out.println(e.getName());
                }
            }

        }
        catch (Exception e)
        {
            System.out.println(e.fillInStackTrace());
        }




    }
}
