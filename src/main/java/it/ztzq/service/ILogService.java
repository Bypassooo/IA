package it.ztzq.service;


public interface ILogService {
    public void findByOffsetAndNodeIdAndMethodAndMessageContains(Long offSet, String nodeId, String method, String checkStr);
    public void findByFunctionidAndServiceidAndMethodAndMessageContains(String functionid, String serviceid, String method, String checkStr);

}
