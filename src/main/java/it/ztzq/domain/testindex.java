package it.ztzq.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

@Document(indexName = "testindex", type = "store")
public class testindex implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Field(index=true, store = true, type = FieldType.Long)
    private Long id;

    @Field(index = true, analyzer = "ik_max_word", store = true, searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String name;

    @Field(index = true, analyzer = "ik_max_word", store = true, searchAnalyzer = "ik_smart", type = FieldType.Integer)
    private Integer age;

    @Field(index = true, store = true, type = FieldType.Date)
    private Date birthday;

    public testindex(){

    }

    public testindex(Long id, String name, Integer age, Date birthday){
        this.id = id;
        this.name = name;
        this.age = age;
        this.birthday = birthday;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
