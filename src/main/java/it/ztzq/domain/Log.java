package it.ztzq.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "logstash-2020.02.28", type = "doc")
public class Log {
    @Id
    private Long id;
    @JsonProperty("MsgId")
    @Field(index = true, type = FieldType.Text, normalizer = "false")
    private String MsgId;
    @JsonProperty("SrcIP")
    @Field(index = true, type = FieldType.Text, normalizer = "false")
    private String SrcIP;
    @Field(index = true, type = FieldType.Text, normalizer = "false")
    private String message;
    @JsonProperty("Buf")
    @Field(index = true, type = FieldType.Text, normalizer = "false")
    private String Buf;
    @Field(index = true, type = FieldType.Text, normalizer = "false")
    private String method;
    @Field(index = true, type = FieldType.Long)
    private Long offset;
    @JsonProperty("NodeId")
    @Field(index = true, type = FieldType.Text, normalizer = "false")
    private String NodeId;
//    @Field(index = true, type = FieldType.Text, normalizer = "false")
//    private String funcid;
//    @Field(index = true, type = FieldType.Text, normalizer = "false")
//    private String serviceid;


    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public String getNodeId() {
        return NodeId;
    }

    public void setNodeId(String nodeId) {
        NodeId = nodeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public String getFuncid() {
//        return funcid;
//    }
//
//    public void setFuncid(String funcid) {
//        this.funcid = funcid;
//    }
//
//    public String getServiceid() {
//        return serviceid;
//    }
//
//    public void setServiceid(String serviceid) {
//        this.serviceid = serviceid;
//    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getBuf() {
        return Buf;
    }

    public void setBuf(String buf) {
        this.Buf = buf;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        this.MsgId = msgId;
    }

    public String getSrcIp() {
        return SrcIP;
    }

    public void setSrcIp(String srcIP) {
        SrcIP = srcIP;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", MsgId='" + MsgId + '\'' +
                ", SrcIP='" + SrcIP + '\'' +
                ", message='" + message + '\'' +
                ", Buf='" + Buf + '\'' +
                ", method='" + method + '\'' +
                ", offset=" + offset +
                ", NodeId='" + NodeId + '\'' +
                '}';
    }
}
