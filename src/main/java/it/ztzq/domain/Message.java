package it.ztzq.domain;

import java.util.Map;

public class Message {
    private String title;
    private String dst;
    private String src;
    private Map<String,String> fieldMap;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Map<String, String> getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(Map<String, String> fieldMap) {
        this.fieldMap = fieldMap;
    }

    @Override
    public String toString() {
        return "Message{" +
                "title='" + title + '\'' +
                ", dst='" + dst + '\'' +
                ", src='" + src + '\'' +
                ", fieldMap=" + fieldMap +
                '}';
    }
}
