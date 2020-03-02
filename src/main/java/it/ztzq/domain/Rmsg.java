package it.ztzq.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "rmsglog_2020.03.02", type = "doc")
public class Rmsg {
    @Id
    private Long id;
    @JsonProperty("OrgMsgid")
    @Field(index = true, type = FieldType.Text, normalizer = "false")
    private String OrgMsgid;
    @Field(index = true, type = FieldType.Text, normalizer = "false")
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgMsgid() {
        return OrgMsgid;
    }

    public void setOrgMsgid(String orgMsgid) {
        OrgMsgid = orgMsgid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Rmsg{" +
                "id=" + id +
                ", OrgMsgid='" + OrgMsgid + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
