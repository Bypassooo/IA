package it.ztzq.config;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class EsConfig {
    private String host = "10.29.185.86";
    private int port = 9200;
    private String schema = "http";
    private int connectTimeOut = 1000;
    private int socketTimeOut = 30000;
    private int connectionRequestTimeOut = 500;
    private int maxConnectNum = 100;
    private int maxConnectPerRoute = 100;
    private HttpHost httpHost;
    private boolean uniqueConnectTimeConfig = true;
    private boolean uniqueConnectNumConfig = true;
    private RestClientBuilder builder;
    private RestHighLevelClient client;

    @Bean(autowire = Autowire.BY_NAME, name="restHighLevelClient")
    public RestHighLevelClient client(){
        httpHost = new HttpHost(host, port, schema);
        builder = RestClient.builder(httpHost);
        if(uniqueConnectTimeConfig){
            setConnectTimeOutConfig();
        }
        if(uniqueConnectNumConfig){
            setMutiConnectConfig();
        }
        client = new RestHighLevelClient(builder);
        return client;
    }

    public void setConnectTimeOutConfig(){
        builder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                requestConfigBuilder.setConnectTimeout(connectTimeOut);
                requestConfigBuilder.setSocketTimeout(socketTimeOut);
                requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeOut);
                return requestConfigBuilder;
            }
        });
    }

    public void setMutiConnectConfig(){
        builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                httpClientBuilder.setMaxConnTotal(maxConnectNum);
                httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
                return httpClientBuilder;
            }
        });
    }

    public void close(){
        if(client != null){
            try{
                client.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
