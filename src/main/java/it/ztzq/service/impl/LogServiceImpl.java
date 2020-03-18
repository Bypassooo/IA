package it.ztzq.service.impl;

import it.ztzq.domain.*;
import it.ztzq.repositories.LogRepository;
import it.ztzq.repositories.RmsgRepository;
import it.ztzq.service.ILogResultService;
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
    //升级之前msg与rmsg的对比
    @Override
    public Set<LogResult> compareLog(String version, String functionid, String serviceid, String method, String checkStr, Pageable pageable,String time) {

        /**************升级前msg数据的处理********************/
        //msg升级前的数据
        List<Log> bUlogs = logRepository.findByFunctionidAndServiceidAndMethodAndTimeAndMessageContainsBeforeUpdate(functionid, serviceid, method, time, checkStr, pageable);
        String bUmsgAns = bUlogs.get(20).getMessage();
        System.out.println(bUmsgAns);
        //获取msg中Ans的map
        Map<String,String> bUmsgAnsMap = splitAnsStrtoList(bUmsgAns);
        Optional<Log> bUoptional = logRepository.findByMsgIdAndMethod(bUlogs.get(0).getMsgId(),"Req");
        String bUmsgReqMessage = bUoptional.get().getMessage();
        //获取msg中Req的map
        Map<String,String> bUmsgReqMap = splitReqStrtoList(bUmsgReqMessage);
        /**************升级后msg数据的处理********************/
        List<Log> aUlogs = logRepository.findByFunctionidAndServiceidAndMethodAndTimeAndMessageContainsAfterUpdate(functionid, serviceid, method, time, checkStr, pageable);
        aUlogs.stream().forEach(log -> System.out.println(log));
        String aUmsgAns = aUlogs.get(2).getMessage();
        Map<String,String> aUmsgAnsMap = splitAnsStrtoList(aUmsgAns);
        Optional<Log> aUoptional =  logRepository.findByMsgIdAndMethod(aUlogs.get(0).getMsgId(),"Req");
        String aUmsgReqMessage = aUoptional.get().getMessage();
        Map<String,String> aUmsgReqMap = splitReqStrtoList(aUmsgReqMessage);
        /**************升级前rmsg数据的处理********************/
        //////////获取rmsg记录
        //获取MsgId找到对应的rmsg
        String bUorgMsgid = bUoptional.get().getMsgId();
        //此处需要考虑返回值为空的情况（后续完善代码）
        Optional<Rmsg> optionalRmsg = rmsgRepository.findByOrgMsgid(bUorgMsgid);
        String bUrmsgReqMessage = optionalRmsg.get().getMessage();
        int bUrmsgReqMessageLength = bUrmsgReqMessage.length();
        String bUlastCh = bUrmsgReqMessage.substring(bUrmsgReqMessageLength-1,bUrmsgReqMessageLength);
        String bUlastSecondCh = bUrmsgReqMessage.substring(bUrmsgReqMessageLength-2,bUrmsgReqMessageLength-1);
        if(bUlastCh.equals(" ") && bUlastSecondCh.equals("&")){
            bUrmsgReqMessage = bUrmsgReqMessage.substring(0,bUrmsgReqMessageLength-2);
        }
        Map<String,String> bUrmsgReqMap = splitReqStrtoList(bUrmsgReqMessage);

        String bUrmsgMsgId = optionalRmsg.get().getMsgId();
        Optional<Rmsg> bUoptionalRmsgAns = rmsgRepository.findByMsgIdAndMethod(bUrmsgMsgId,"Ans");
        String bUrmsgAnsMessage = bUoptionalRmsgAns.get().getMessage();
        Map<String,String> bUrmsgAnsMap = splitAnsStrtoList(bUrmsgAnsMessage);

        /**************升级后rmsg数据的处理********************/

        String aUorgMsgid = aUoptional.get().getMsgId();
        Optional<Rmsg> aUoptionalRmsg = rmsgRepository.findByOrgMsgid(aUorgMsgid);
        String aUrmsgReqMessage = aUoptionalRmsg.get().getMessage();
        int aUrmsgReqMessageLength = aUrmsgReqMessage.length();
        String aUlastCh = aUrmsgReqMessage.substring(aUrmsgReqMessageLength-1,aUrmsgReqMessageLength);
        String aUlastSecondCh = aUrmsgReqMessage.substring(aUrmsgReqMessageLength-2,aUrmsgReqMessageLength-1);
        if(aUlastCh.equals(" ") && aUlastSecondCh.equals("&")){
            aUrmsgReqMessage = aUrmsgReqMessage.substring(0,aUrmsgReqMessageLength-2);
        }
        Map<String,String> aUrmsgReqMap = splitReqStrtoList(aUrmsgReqMessage);

        String aUrmsgMsgId = aUoptionalRmsg.get().getMsgId();
        Optional<Rmsg> aUoptionalRmsgAns = rmsgRepository.findByMsgIdAndMethod(aUrmsgMsgId,"Ans");
        String aUrmsgAnsMessage = aUoptionalRmsgAns.get().getMessage();
        Map<String,String> aUrmsgAnsMap = splitAnsStrtoList(aUrmsgAnsMessage);



        Map<String,Message> transInputMap = new HashMap<>();
        Map<String,Message> transOutputMap = new HashMap<>();
        Map<String,String> bUmsgReqTransMap = new HashMap<>();
        Map<String,String> bUmsgAnsTransMap = new HashMap<>();
        Map<String,String> aUmsgReqTransMap = new HashMap<>();
        Map<String,String> aUmsgAnsTransMap = new HashMap<>();


        Set<LogResult> logResultReqSet0 = new HashSet<>();
        Set<LogResult> logResultAnsSet0 = new HashSet<>();
        Set<LogResult> logResultReqSet1 = new HashSet<>();
        Set<LogResult> logResultAnsSet1 = new HashSet<>();
        Set<LogResult> logResultReqSet2 = new HashSet<>();
        Set<LogResult> logResultAnsSet2 = new HashSet<>();
        Set<LogResult> logResultReqSet3 = new HashSet<>();
        Set<LogResult> logResultAnsSet3 = new HashSet<>();
        Set<LogResult> logResultSet = new HashSet<>();



        //获取转换文件
        try {
            transInputMap = getXmlTransinfo("trans/Kingdom_To_OTC.xml","input");
            transOutputMap = getXmlTransinfo("trans/Kingdom_To_OTC.xml","output");
        }catch (Exception e){
            System.out.println(e.fillInStackTrace());
        }

        bUmsgReqTransMap = transMapKey(bUmsgReqMap,transInputMap,functionid);
        aUmsgReqTransMap = transMapKey(aUmsgReqMap,transInputMap,functionid);
        bUmsgAnsTransMap = transMapKey(bUmsgAnsMap,transOutputMap,functionid);
        aUmsgAnsTransMap = transMapKey(aUmsgAnsMap,transOutputMap,functionid);


            /*
    用来说明是哪种文件的对比
            0   升级前msg与rmsg对比：
            1   升级后msg与rmsg对比：
            2   升级后msg与升级后msg对比：
            3   升级后rmsg与升级后rmsg对比：
     */
        //升级前msg与rmsg对比
        logResultReqSet0 = comparTwoMap(bUmsgReqTransMap,bUrmsgReqMap,version,0,0);
        logResultAnsSet0 = comparTwoMap(bUmsgAnsTransMap,bUrmsgAnsMap,version,1,0);
        //升级后msg与rmsg对比
        logResultReqSet1 = comparTwoMap(aUmsgReqTransMap,aUrmsgReqMap,version,0,1);
        logResultAnsSet1 = comparTwoMap(aUmsgAnsTransMap,aUrmsgAnsMap,version,1,1);
        //msg升级前后对比
        logResultReqSet2 = comparTwoMap(bUmsgReqTransMap,aUmsgReqTransMap,version,0,2);
        logResultAnsSet2 = comparTwoMap(bUmsgAnsTransMap,aUmsgAnsTransMap,version,1,2);
        //rmsg升级前后对比
        logResultReqSet3 = comparTwoMap(bUrmsgReqMap,aUrmsgReqMap,version,0,3);
        logResultAnsSet3 = comparTwoMap(bUrmsgAnsMap,aUrmsgAnsMap,version,1,3);

        logResultSet.addAll(logResultReqSet0);
        logResultSet.addAll(logResultAnsSet0);
        logResultSet.addAll(logResultReqSet1);
        logResultSet.addAll(logResultAnsSet1);
        logResultSet.addAll(logResultReqSet2);
        logResultSet.addAll(logResultAnsSet2);
        logResultSet.addAll(logResultReqSet3);
        logResultSet.addAll(logResultAnsSet3);

        return logResultSet;

    }

    public Map<String,String> transMapKey(Map<String,String> map1, Map<String,Message> map2, String functionid){

        Message inputMessage = map2.get(functionid);
        Map<String,String> funcInputTransMap = inputMessage.getFieldMap();
        Map<String,String> msgReqTransMap = new HashMap<String,String>();
        for(Map.Entry<String,String> entry:map1.entrySet()){
            //考虑转换关系中没有的情况
            String src = entry.getKey();
            int direction = 1;//Req时
            //如果是Req则是需要由src转为dst
            //如果是Ans则是需要由dst转为src

            String dst = funcInputTransMap.get(src);
            if(dst == null){
                dst = src;
            }
            msgReqTransMap.put(dst,entry.getValue());
        }
        return msgReqTransMap;
    }
    public Set<LogResult> comparTwoMap(Map<String,String> map1, Map<String,String> map2, String version, int direction, int abvalue){
        //将msgReqTransMap与rmsgReqMap对比
        //首先处理key的交集部分
        Set<String> msgReqTransKey = map1.keySet();
        Set<String> rmsgReqKey = map2.keySet();
        //rmsgkey中存在空的key，应该去掉
        //key的交集
        Set<String> keyInter = new HashSet<>();
        Set<String> msgDiff = new HashSet<>();
        Set<String> rmsgDiff = new HashSet<>();

        Set<Result> resultReqSet = new HashSet<>();
        Set<LogResult> logResultSet = new HashSet<>();
        keyInter.addAll(msgReqTransKey);
        keyInter.retainAll(rmsgReqKey);
        //msg中有rmsg中没有的那部分
        msgDiff = Sets.difference(msgReqTransKey,keyInter);
        //msg中没有rmsg有的那部分
        rmsgDiff = Sets.difference(rmsgReqKey,keyInter);

        for(String str:keyInter){
            LogResult logResult = new LogResult();
            logResult.setVersion(version);
            logResult.setDirection(direction);
            logResult.setAbvalue(abvalue);
            String msgValue = map1.get(str);
            String rmsgValue = map2.get(str);
            if(msgValue.equals(rmsgValue)){
                logResult.setFlag(0);
            }
            else{
                logResult.setFlag(3);
            }
            logResult.setKey(str);
            logResult.setValuea(msgValue);
            logResult.setValueb(rmsgValue);
            logResultSet.add(logResult);
        }
        //第二部处理msgReq中比rmsg中多的那一部分
        for(String str:msgDiff){
            LogResult logResult = new LogResult();
            String msgValue = map1.get(str);
            logResult.setKey(str);
            logResult.setValuea(msgValue);
            logResult.setValueb("");
            logResult.setFlag(1);
            logResult.setVersion(version);
            logResult.setDirection(direction);
            logResult.setAbvalue(abvalue);
            logResultSet.add(logResult);
        }
        //第三部分处理rmsg中比msg中多的那一部分
        for(String str:rmsgDiff){
            LogResult logResult = new LogResult();
            String rmsgValue = map2.get(str);
            logResult.setKey(str);
            logResult.setValueb(rmsgValue);
            logResult.setValuea("");
            logResult.setFlag(2);
            logResult.setVersion(version);
            logResult.setDirection(direction);
            logResult.setAbvalue(abvalue);
            logResultSet.add(logResult);
        }
        return logResultSet;
    }
    public Map<String,String> splitAnsStrtoList(String message){

        String compareStr = message.substring(message.indexOf("_RS_2="));
        List<String> resultList = new ArrayList<String>();
        Map<String,String> resultMap = new HashMap<String,String>();

        for(String str:compareStr.split(";")){
            resultList.add(str);
        }
        //将keyname分开
        String[] keyName = resultList.get(2).split(",");
        int checkFlag = resultList.get(3).indexOf("&_EORS");
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

        return resultMap;

    }
    public Map<String, String> splitReqStrtoList(String message){
        //buf之后的字符串
        String bufStr = message.substring(message.indexOf("Buf"));
        //将buf中需要比对的字符串截取出来
        String compareStr = bufStr.substring(130);
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
                if(direction.equals("input")) {
                    for (Element field : fieldList) {
                        fieldMap.put(field.getAttributeValue("src"), field.getAttributeValue("dst"));
                    }
                }
                else{
                    for (Element field : fieldList) {
                        fieldMap.put(field.getAttributeValue("dst"), field.getAttributeValue("src"));
                    }
                }
                message.setFieldMap(fieldMap);
            }
            xmlMap.put(e.getAttributeValue("src"),message);
        }
        return xmlMap;
    }

}
