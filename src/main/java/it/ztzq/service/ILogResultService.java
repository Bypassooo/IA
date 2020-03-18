package it.ztzq.service;

import it.ztzq.domain.LogResult;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface ILogResultService {
    public void createIndex();
    public void addDocument(LogResult logResult);
    public Set<LogResult> getLogResultsByVersion(String version);
}
