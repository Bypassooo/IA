package it.ztzq.repositories;

import it.ztzq.domain.Log;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface LogRepository extends ElasticsearchRepository<Log, String> {
    List<Log> findByPid(String pid);
    @Query("{\"bool\" : {\"must\" : {\"term\" : {\"NodeId\" : \"?0\"}}}}")
    List<Log> findByNodeId(String NodeId);
    List<Log> findByBuf(String Buf);
    List<Log> findByMethod(String method);
    List<Log> findByMsgId(String MsgId);
}
