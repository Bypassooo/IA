package it.ztzq.service.impl;

import it.ztzq.domain.LogResult;
import it.ztzq.repositories.LogResultRepository;
import it.ztzq.service.ILogResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service("logresult")
public class LogResultImpl implements ILogResultService {

    @Autowired
    private LogResultRepository logResultRepository;

    @Autowired
    private ElasticsearchTemplate template;

    @Override
    public void createIndex() {
        //创建索引，并配置映射关系
        template.createIndex(LogResult.class);
        //配置映射关系
        //  template.putMapping(Article.class);
    }

    @Override
    public void addDocument(LogResult lr) {
        LogResult logResult = new LogResult();
        String id = UUID.randomUUID().toString();
        logResult.setVersion(lr.getVersion());
        logResult.setKey(lr.getKey());
        logResult.setValuea(lr.getValuea());
        logResult.setValueb(lr.getValueb());
        logResult.setAbvalue(lr.getAbvalue());
        logResult.setDirection(lr.getDirection());
        logResult.setFlag(lr.getFlag());
        logResult.setId(id);
        logResultRepository.save(logResult);
    }

    @Override
    public Set<LogResult> getLogResultsByVersion(String version) {
        Set<LogResult> logResults = logResultRepository.findByVersion(version);
        return logResults;
    }
}
