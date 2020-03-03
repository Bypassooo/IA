package it.ztzq.service;


import org.springframework.data.domain.Pageable;

public interface ILogService {
    public void findByOffsetAndNodeIdAndMethodAndMessageContains(Long offSet, String nodeId, String method, String checkStr);
    public void findByFunctionidAndServiceidAndMethodAndMessageContains(String functionid, String serviceid, String method, String checkStr, Pageable pageable);

}
