package it.ztzq.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "msglog_2020.03.02", type = "doc")
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
    @Field(index = true, type = FieldType.Text, normalizer = "false")
    private String functionid;
    @Field(index = true, type = FieldType.Text, normalizer = "false")
    private String serviceid;
    @Field(index = true, type = FieldType.Text, normalizer = "false")
    private String systemtype;


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

    public String getSrcIP() {
        return SrcIP;
    }

    public void setSrcIP(String srcIP) {
        SrcIP = srcIP;
    }

    public String getFunctionid() {
        return functionid;
    }

    public void setFunctionid(String functionid) {
        this.functionid = functionid;
    }

    public String getServiceid() {
        return serviceid;
    }

    public void setServiceid(String serviceid) {
        this.serviceid = serviceid;
    }

    public String getSystemtype() {
        return systemtype;
    }

    public void setSystemtype(String systemtype) {
        this.systemtype = systemtype;
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
                ", functionid='" + functionid + '\'' +
                ", serviceid='" + serviceid + '\'' +
                ", systemtype='" + systemtype + '\'' +
                '}';
    }
}
