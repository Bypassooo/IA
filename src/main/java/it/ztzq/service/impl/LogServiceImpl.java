package it.ztzq.service.impl;

import it.ztzq.domain.Log;
import it.ztzq.domain.Message;
import it.ztzq.domain.Rmsg;
import it.ztzq.repositories.LogRepository;
import it.ztzq.repositories.RmsgRepository;
import it.ztzq.service.ILogService;
import javafx.beans.binding.LongExpression;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resources;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service("logService")
public class LogServiceImpl implements ILogService {

    @Autowired
    private LogRepository logRepository;
    @Autowired
    private RmsgRepository repository;
    @Override
    public void findByOffsetAndNodeIdAndMethodAndMessageContains(Long offSet, String nodeId, String method, String checkStr) {
        List<Log> logs = logRepository.findByOffsetAndNodeIdAndMethodAndMessageContains(offSet, nodeId, method, checkStr);
        //一个功能号只有一条应答（确认是否只有一条应答)
        Optional<Log> optional = logRepository.findByMsgIdAndMethod(logs.get(0).getMsgId(),"Req");
        String msgMessage = optional.get().getMessage();
        Map<String,String> msgMap = splitStrtoList(msgMessage);
        //////////获取rmsg记录
        //获取MsgId找到对应的rmsg
        String orgMsgid = optional.get().getMsgId();
        orgMsgid = "6680012102DC6C00222CC85C";
        //此处需要考虑返回值为空的情况（后续完善代码）
        Optional<Rmsg> optionalRmsg = repository.findByOrgMsgid(orgMsgid);
        String rmsgMessage = optionalRmsg.get().getMessage();
        System.out.println(rmsgMessage);
        Map<String,String> rmsgMap = splitStrtoList(rmsgMessage);
        for(Map.Entry<String,String> entry:rmsgMap.entrySet()){
            System.out.println(entry.getKey()+"="+entry.getValue());
        }

        //获取转换文件
        try {
            List<Message> transList = getXmlTransinfo("trans/KESB_TO_KSOT.xml");
        }catch (Exception e){
            System.out.println(e.fillInStackTrace());
        }
        // 将rmsg中的字段进行转换
    }
    public Map<String, String> splitStrtoList(String message){
        //buf之后的字符串
        String bufStr = message.substring(message.indexOf("Buf"));
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
        return strMap;
    }
    //  trans/KESB_To_KFMS.xml
    public List<Message> getXmlTransinfo(String path) throws Exception {
        InputStream in = LogServiceImpl.class.getClassLoader().getResourceAsStream(path);
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
//        //检查数据是否都存入List中
//        for(Message m:messageList){
//            System.out.println(m.getTitle());
//            System.out.println(m.getDst());
//            System.out.println(m.getSrc());
//            for(Map.Entry<String,String> entry:m.getFieldMap().entrySet()){
//                System.out.println("dst="+entry.getKey()+",src="+entry.getValue());
//            }
//        }
        return messageList;
    }

}
