package it.ztzq.test;

import com.mysql.cj.QueryBindings;
import it.ztzq.entity.Article;
import it.ztzq.repositories.ArticleRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SpringDataElasticSearchTest {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ElasticsearchTemplate template;
    @Test
    public void createIndex() throws Exception{
        //创建索引，并配置映射关系
        template.createIndex(Article.class);
        //配置映射关系
      //  template.putMapping(Article.class);
    }
    @Test
    public void addDocument() throws Exception{
        //创建一个Article对象
        Article article = new Article();
        article.setId(1);
        article.setTitle("test1");
        article.setContent("this is test1");
        articleRepository.save(article);
    }
    @Test
    public void deleteDocumentByID() throws Exception{
        articleRepository.deleteById(1l);
        //全部删除
        articleRepository.deleteAll();
    }
    @Test
    public void findAll() throws Exception{
        Iterable<Article> articles = articleRepository.findAll();
        articles.forEach(a-> System.out.println(a));
    }

    @Test
    public  void findById() throws Exception{
        Optional<Article> optional = articleRepository.findById(1l);
        Article article = optional.get();
        System.out.println(article);
    }
    @Test
    public void findByTitle() throws Exception{
        List<Article> list  = articleRepository.findByTitle("test");
        list.stream().forEach(article -> System.out.println(article));
    }

    @Test
    public void findByContentOrTitle() throws Exception{
        //设置分页信息
        Pageable pageable = PageRequest.of(0,15);
        articleRepository.findByContentOrTitle("test","test", pageable).forEach(a-> System.out.println(a));
    }

    //如果更灵活，则需要使用原生的查询方式NativeSearchQuery
    //使用方法： 1> 创建一个NativeSearchQuery对象，使用QueryBuilder对象设置查询条件
    //         2> 使用ElasticsearchTemplate对象执行查询
    //         3> 取查询结果
    @Test
    public void testNativeSearchQuery() throws Exception{
       //创建一个查询对象
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.queryStringQuery("test1").defaultField("title"))
                .withPageable(PageRequest.of(0,15))
                .build();
        // 执行查询
        List<Article> articleList = template.queryForList(query,Article.class);
        articleList.forEach(a-> System.out.println(a));
    }
}
