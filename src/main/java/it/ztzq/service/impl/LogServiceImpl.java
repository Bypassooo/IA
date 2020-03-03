package it.ztzq.service.impl;

import it.ztzq.domain.Log;
import it.ztzq.domain.Message;
import it.ztzq.domain.Result;
import it.ztzq.domain.Rmsg;
import it.ztzq.repositories.LogRepository;
import it.ztzq.repositories.RmsgRepository;
import it.ztzq.service.ILogService;
import org.elasticsearch.common.util.set.Sets;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Service("logService")
public class LogServiceImpl implements ILogService {

    @Autowired
    private LogRepository logRepository;
    @Autowired
    private RmsgRepository rmsgRepository;
    @Override
    public void findByOffsetAndNodeIdAndMethodAndMessageContains(Long offSet, String nodeId, String method, String checkStr) {
        int a = 1;
    }
    @Override
    public void findByFunctionidAndServiceidAndMethodAndMessageContains(String functionid, String serviceid, String method, String checkStr, Pageable pageable){

        List<Log> logs = logRepository.findByFunctionidAndServiceidAndMethodAndMessageContains(functionid, serviceid, method, checkStr, pageable);
        String msgAns = logs.get(34).getMessage();
        //获取msg中Ans的map
        Map<String,String> msgAnsMap = splitAnsStrtoList(msgAns);
        //一个功能号只有一条应答（确认是否只有一条应答)
        Optional<Log> optional = logRepository.findByMsgIdAndMethod(logs.get(0).getMsgId(),"Req");
        String msgReqMessage = optional.get().getMessage();
        //获取msg中Req的map
        Map<String,String> msgReqMap = splitReqStrtoList(msgReqMessage);
        //////////获取rmsg记录
        //获取MsgId找到对应的rmsg
        String orgMsgid = optional.get().getMsgId();
        //此处需要考虑返回值为空的情况（后续完善代码）
        System.out.println(orgMsgid);
        Optional<Rmsg> optionalRmsg = rmsgRepository.findByOrgMsgid(orgMsgid);
        String rmsgReqMessage = optionalRmsg.get().getMessage();
        Map<String,String> rmsgReqMap = splitReqStrtoList(rmsgReqMessage);

        String rmsgMsgId = optionalRmsg.get().getMsgId();
        Optional<Rmsg> optionalRmsgAns = rmsgRepository.findByMsgIdAndMethod(rmsgMsgId,"Ans");
        String rmsgAnsMessage = optionalRmsgAns.get().getMessage();
        Map<String,String> rmsgAnsMap = splitAnsStrtoList(rmsgAnsMessage);

        Map<String,Message> transInputMap = new HashMap<>();
        Map<String,Message> transOutputMap = new HashMap<>();
        Map<String,String> msgReqTransMap = new HashMap<>();
        Map<String,String> msgAnsTransMap = new HashMap<>();

        Set<Result> resultReqSet = new HashSet<>();
        Set<Result> resultAnsSet = new HashSet<>();


        //获取转换文件
        try {
            transInputMap = getXmlTransinfo("trans/Kingdom_To_OTC.xml","input");
            transOutputMap = getXmlTransinfo("trans/Kingdom_To_OTC.xml","output");
        }catch (Exception e){
            System.out.println(e.fillInStackTrace());
        }
        msgReqTransMap = transMapKey(msgReqMap,transInputMap,functionid);
        msgAnsTransMap = transMapKey(msgAnsMap,transOutputMap,functionid);

        resultReqSet = comparTwoMap(msgReqTransMap,rmsgReqMap);
        resultAnsSet = comparTwoMap(msgAnsTransMap,rmsgAnsMap);

        for(Result a:resultReqSet){
            System.out.println(a.getFlag());
            if(a.getFlag() != 0){
                System.out.println("key = "+a.getKey()+" ,msgReq = "+a.getMsgvalue()+" ,rmsgReq = "+a.getRmsgvalue());
            }
        }

    }
    public Map<String,String> transMapKey(Map<String,String> map1,Map<String,Message> map2, String functionid){

        Message inputMessage = map2.get(functionid);
        Map<String,String> funcInputTransMap = inputMessage.getFieldMap();
        Map<String,String> msgReqTransMap = new HashMap<String,String>();
        for(Map.Entry<String,String> entry:map1.entrySet()){
            //考虑转换关系中没有的情况
            String src = entry.getKey();
            String dst = funcInputTransMap.get(src);
            if(dst == null){
                dst = src;
            }
            msgReqTransMap.put(dst,entry.getValue());
        }
        return msgReqTransMap;
    }
    public Set<Result> comparTwoMap(Map<String,String> map1, Map<String,String> map2){
        //将msgReqTransMap与rmsgReqMap对比
        //首先处理key的交集部分
        Set<String> msgReqTransKey = map1.keySet();
        Set<String> rmsgReqKey = map2.keySet();
        //key的交集
        Set<String> keyInter = new HashSet<>();
        Set<String> msgDiff = new HashSet<>();
        Set<String> rmsgDiff = new HashSet<>();

        Set<Result> resultReqSet = new HashSet<>();
        keyInter.addAll(msgReqTransKey);
        keyInter.retainAll(rmsgReqKey);
        //msg中有rmsg中没有的那部分
        msgDiff = Sets.difference(msgReqTransKey,keyInter);
        //msg中没有rmsg有的那部分
        rmsgDiff = Sets.difference(rmsgReqKey,keyInter);
        Result result = new Result();
        for(String str:keyInter){
            String msgValue = map1.get(str);
            String rmsgValue = map2.get(str);
            if(msgValue == rmsgValue){
                result.setFlag(0);
            }
            else{
                result.setFlag(3);
            }
            result.setKey(str);
            result.setMsgvalue(msgValue);
            result.setRmsgvalue(rmsgValue);
            resultReqSet.add(result);
            result.clear();
        }
        //第二部处理msgReq中比rmsg中多的那一部分
        for(String str:msgDiff){
            String msgValue = map1.get(str);
            result.setKey(str);
            result.setMsgvalue(msgValue);
            result.setRmsgvalue("");
            result.setFlag(1);
            resultReqSet.add(result);
            result.clear();
        }
        //第三部分处理rmsg中比msg中多的那一部分
        for(String str:rmsgDiff){
            String rmsgValue = map2.get(str);
            result.setKey(str);
            result.setRmsgvalue(rmsgValue);
            result.setMsgvalue("");
            result.setFlag(2);
            resultReqSet.add(result);
            result.clear();
        }
        return resultReqSet;
    }
    public Map<String,String> splitAnsStrtoList(String message){

        String compareStr = message.substring(message.indexOf("_RS_2="));
        System.out.println(compareStr);
        List<String> resultList = new ArrayList<String>();
        Map<String,String> resultMap = new HashMap<String,String>();

        for(String str:compareStr.split(";")){
            resultList.add(str);
        }
        //将keyname分开
        String[] keyName = resultList.get(2).split(",");
        int checkFlag = resultList.get(3).indexOf("&_EORS");
        System.out.println("checkFlag"+checkFlag);
        //value为空
        if(checkFlag == 1){
            String[] value = resultList.get(4).split(":");
            for(int i=0; i< keyName.length;i++){
                resultMap.put(keyName[i],value[1]);
            }

        }
        else{
            String value = resultList.get(3);
            int beginStr = value.indexOf("=");
            int endStr = value.indexOf("&_EORS_");
            String keyCheck = value.substring(beginStr,endStr);
            System.out.println(keyCheck);
            String[] keyValue = keyCheck.split(",");
            if(keyName.length == keyValue.length){
                for(int i = 0; i < keyName.length; i++){
                    resultMap.put(keyName[i],keyValue[i]);
                }
            }
            else if(keyName.length > keyValue.length){
                for(int i = 0; i < keyName.length; i++){
                    if(i >= keyValue.length){
                        resultMap.put(keyName[i],"");
                    }
                    else{
                        resultMap.put(keyName[i],keyValue[i]);
                    }
                }
            }
            else{
                String valuSurplus = "";
                for(int i = 0; i < keyValue.length; i++){

                    if(i >= keyName.length){
                        valuSurplus += keyValue[i];
                    }
                    else{
                        resultMap.put(keyName[i],keyValue[i]);
                    }
                }
                //将多余的value放到最后一个键值对里面
                resultMap.put("valueSurplus",valuSurplus);
            }
        }
        for(Map.Entry<String,String>entry:resultMap.entrySet()){
            System.out.println(entry.getKey() +"="+ entry.getValue());
        }

        return resultMap;

    }
    public Map<String, String> splitReqStrtoList(String message){
        //buf之后的字符串
        String bufStr = message.substring(message.indexOf("Buf"));
        //将buf中需要比对的字符串截取出来
        String compareStr = bufStr.substring(130);
        System.out.println(compareStr);
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
    public Map<String,Message> getXmlTransinfo(String path, String direction) throws Exception {
        InputStream in = LogServiceImpl.class.getClassLoader().getResourceAsStream(path);
        SAXBuilder sb = new SAXBuilder();
        Document document = sb.build(in);
        //获取根节点
        Element root = document.getRootElement();
        List<Element> childList = root.getChildren();
        List<Message> messageList =  new ArrayList<Message>();
        Map<String,Message> xmlMap = new HashMap<String,Message>();

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
                Element inputElement = e.getChild(direction);
                //获取field节点列表
                List<Element> fieldList = inputElement.getChildren();
                Map<String,String> fieldMap = new HashMap<String,String>();
                for(Element field :fieldList){
                    fieldMap.put(field.getAttributeValue("dst"),field.getAttributeValue("src"));
                }
                message.setFieldMap(fieldMap);
            }
            xmlMap.put(e.getAttributeValue("src"),message);
         //   messageList.add(message);
        }
        return xmlMap;
    }

}
