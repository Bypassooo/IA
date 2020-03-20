package it.ztzq.service;

import it.ztzq.domain.LogResult;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface ILogResultService {
    public void createIndex();
    public void addDocument(LogResult logResult);
    public Page<LogResult> getLogResultsByVersion(String version,Integer page, Integer size);
}
