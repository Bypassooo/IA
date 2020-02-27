package it.ztzq.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "logstash-2020.02.26", type = "doc")
public class Log {
    @Id
    @Field(index = true, type = FieldType.Text, normalizer = "false")
    private String MsgId;
    @Field(index = true, type = FieldType.Text, normalizer = "false")
    private String pid;
    @Field(index = true, type = FieldType.Text, normalizer = "false")
    private String message;
    @JsonProperty("NodeId")
    @Field(index = true, type = FieldType.Text)
    private String NodeId;
    @JsonProperty("Buf")
    @Field(index = true, type = FieldType.Text, normalizer = "false")
    private String Buf;
    @Field(index = true, type = FieldType.Text, normalizer = "false")
    private String method;

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

    public String getNodeId() {
        return NodeId;
    }

    public void setNodeId(String nodeId) {
        this.NodeId = nodeId;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        this.MsgId = msgId;
    }

    public String getPid() {
        return pid;
    }
    public void setPid(String pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "Log{" +
                "MsgId='" + MsgId + '\'' +
                ", pid='" + pid + '\'' +
                ", message='" + message + '\'' +
                ", NodeId='" + NodeId + '\'' +
                ", Buf='" + Buf + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
