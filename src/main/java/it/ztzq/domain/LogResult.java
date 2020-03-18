package it.ztzq.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "logresult", type = "doc")
public class LogResult {
    @Id
    private String id;
    @Field(index = true, type = FieldType.Text)
    private String version;
    @Field(index = true, type = FieldType.Text)
    private String key;
    @Field(index = true, type = FieldType.Text)
    private String valuea;
    @Field(index = true, type = FieldType.Text)
    private String valueb;
    @Field(index = true, type = FieldType.Integer)
    private int flag;
    /*
    用来判断是否相等,
            0   表示a和b中两边参数一致，
            1   表示a中有b中没有
            2   表示a中没有b中有
            3   表示a和b两边都有但是值不一样
    */
    @Field(index = true, type = FieldType.Integer)
    private int direction;
    /*
   用来说明是请求还是应答
           0    Req
           1    Ans
   */
    @Field(index = true, type = FieldType.Integer)
    private int abvalue;
    /*
    用来说明是哪种文件的对比
            0   升级前msg与rmsg对比：
            1   升级后msg与rmsg对比：
            2   升级后msg与升级后rmsg对比：
            3   升级后rmsg与升级后rmsg对比：
     */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValuea() {
        return valuea;
    }

    public void setValuea(String valuea) {
        this.valuea = valuea;
    }

    public String getValueb() {
        return valueb;
    }

    public void setValueb(String valueb) {
        this.valueb = valueb;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getAbvalue() {
        return abvalue;
    }

    public void setAbvalue(int abvalue) {
        this.abvalue = abvalue;
    }

    @Override
    public String toString() {
        return "LogResult{" +
                "id='" + id + '\'' +
                ", version='" + version + '\'' +
                ", key='" + key + '\'' +
                ", valuea='" + valuea + '\'' +
                ", valueb='" + valueb + '\'' +
                ", flag=" + flag +
                ", direction=" + direction +
                ", abvalue=" + abvalue +
                '}';
    }

    public void clear(){
        this.setKey("");
        this.setVersion("");
        this.setValuea("");
        this.setValueb("");
        this.setFlag(4);
        this.setDirection(2);
        this.setAbvalue(4);
    }

}
