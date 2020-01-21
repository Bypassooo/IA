package it.ztzq.controller;

import com.alibaba.fastjson.JSONObject;
import io.netty.util.internal.StringUtil;
import it.ztzq.util.EsUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.utils.DateUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Response;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

@RestController
@RequestMapping("/es")
public class EsController {
    private static  final Logger logger = LoggerFactory.getLogger(EsController.class);

    private String indexName = "newindex";

    private String esType = "normal";

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/createIndex")
    @ResponseBody
    public String createIndex(HttpServletRequest request, HttpServletResponse response){
        if(!EsUtils.checkIndexExist(indexName)){
            if(EsUtils.createIndex(indexName)){
                return "索引创建成功";
            }else {
                return "索引创建失败";
            }
        }else {
            return "索引已经存在";
        }
    }

    @RequestMapping("/addData")
    @ResponseBody
    public String addData(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",DateUtils.formatDate(new Date()));
        jsonObject.put("age",29);
        jsonObject.put("name","ycy");
        jsonObject.put("date",new Date());
        String id = EsUtils.addData(indexName,esType,jsonObject.getString("id"),jsonObject);
        if(StringUtil.isNullOrEmpty(id)){
            return "插入成功";
        }
        else {
            return "插入失败";
        }
    }

    @RequestMapping("/queryAll")
    @ResponseBody
    public String queryAll(){
        try{
            HttpEntity entity = new NStringEntity("{ \"query\":{\"match_all\":{}}}", ContentType.APPLICATION_JSON);
            String endPoint = "/" + indexName + "/" + esType + "/_search";
            Response response = EsUtils.getLowLevelClient().performRequest("POST",endPoint, Collections.<String,String>emptyMap(),entity);
            return EntityUtils.toString(response.getEntity());
        }catch (IOException e){
            e.printStackTrace();
        }
        return "查询出错";
    }

    @RequestMapping("/queryByMatch")
    @ResponseBody
    public String queryByMatch(){
        try{
            String endPoint = "/" + indexName + "/" + esType + "/_search";
            IndexRequest indexRequest = new IndexRequest();
            XContentBuilder builder;
            try{
                builder = JsonXContent.contentBuilder().startObject().startObject("query").startObject("match").field("name.keyword","zjj").endObject().endObject().endObject();
                indexRequest.source(builder);
            }catch (IOException e){
                e.printStackTrace();
            }

            String source = indexRequest.source().utf8ToString();

            logger.info("source---->"+source);

            HttpEntity entity = new NStringEntity(source,ContentType.APPLICATION_JSON);
            Response response = EsUtils.getLowLevelClient().performRequest("POST",endPoint,Collections.<String, String>emptyMap(),entity);
            return EntityUtils.toString(response.getEntity());
        }catch (IOException e){
            e.printStackTrace();
        }
        return "查询数据出错";
    }

    @RequestMapping("/queryByCompound")
    @ResponseBody
    public String queryByCompound(){
        try{
            String endPoint = "/" + indexName + "/" + esType + "/_search";
            IndexRequest indexRequest = new IndexRequest();
            XContentBuilder builder;
            try{
                builder = JsonXContent.contentBuilder().
                        startObject()
                        .startObject("query")
                            .startObject("bool")
                                .startObject("must")
                                    .startObject("match")
                                        .field("name.keyword","ycy")
                                    .endObject()
                                .endObject()
                                .startObject("filter")
                                    .startObject("range")
                                        .startObject("age")
                                            .field("gte","20")
                                            .field("lte","30")
                                        .endObject()
                                    .endObject()
                                .endObject()
                            .endObject()
                        .endObject()
                        .endObject();
                indexRequest.source(builder);
            }catch (IOException e){
                e.printStackTrace();
            }

            String source = indexRequest.source().utf8ToString();
            logger.info("source---->"+source);
            HttpEntity entity = new NStringEntity(source,ContentType.APPLICATION_JSON);
            Response response = EsUtils.getLowLevelClient().performRequest("POST",endPoint,Collections.<String,String>emptyMap(),entity);
            return EntityUtils.toString(response.getEntity());
        }catch (IOException e){
            e.printStackTrace();
        }
        return "查询数据出错";
    }

    @RequestMapping("/delByQuery")
    @ResponseBody
    public String delByQuery(){
        String deleteText = "chy";
        String endPoint = "/" + indexName + "/" +esType + "/_delete_by_query";

        IndexRequest indexRequest = new IndexRequest();
        XContentBuilder builder;
        try{
            builder = JsonXContent.contentBuilder()
                    .startObject()
                    .startObject("query")
                    .startObject("term")
                    .field("name.keyword",deleteText)
                    .endObject()
                    .endObject()
                    .endObject();
            indexRequest.source(builder);
        }catch (IOException e){
            e.printStackTrace();
        }

        String source = indexRequest.source().utf8ToString();
        HttpEntity entity = new NStringEntity(source,ContentType.APPLICATION_JSON);
        try{
            Response response = EsUtils.getLowLevelClient().performRequest("POST",endPoint,Collections.<String,String>emptyMap(),entity);
            return EntityUtils.toString(response.getEntity());
        }catch (IOException e){
            e.printStackTrace();
        }
        return "删除错误";
    }




}
