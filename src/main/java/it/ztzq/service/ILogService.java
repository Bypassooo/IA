package it.ztzq.service;

import it.ztzq.domain.Log;

import java.util.List;

public interface ILogService {
    public void findByOffsetAndNodeIdAndMethodAndMessageContains(Long offSet, String nodeId, String method, String checkStr);
}
