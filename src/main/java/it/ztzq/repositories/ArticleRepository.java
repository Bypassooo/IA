package it.ztzq.repositories;

import it.ztzq.entity.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ArticleRepository extends ElasticsearchRepository<Article, Long> {
    //根据命名规则来命名，则springdata会帮助我们实现，所以我们只需定义即可
    //此时会对所搜索的词进行分词，但是分词后每个词之间都是and的关系
    List<Article> findByTitle(String title);
    //最后一个参数是设置分页信息
    List<Article> findByContentOrTitle(String content, String title, Pageable pageable);

}
