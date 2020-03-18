package it.ztzq.repositories;

import it.ztzq.domain.LogResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Set;

public interface LogResultRepository extends ElasticsearchRepository<LogResult, String> {
    Set<LogResult> findByVersion(String version);
}
