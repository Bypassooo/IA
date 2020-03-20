package it.ztzq.repositories;

import it.ztzq.domain.LogResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface LogResultRepository extends ElasticsearchRepository<LogResult, String> {
    Page<LogResult> findByVersion(String version, Pageable pageable);
}
