package it.ztzq.repositories;

import it.ztzq.domain.Log;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

public interface LogRepository extends ElasticsearchRepository<Log, String> {

    @Query("{\"bool\" : {\"must\" : {\"term\" : {\"Buf\" : \"?0\"}}}}")
    List<Log> findByBuf(String Buf);
    List<Log> findByMethod(String method);
    @Query("{\"bool\":{\"must\":[{\"match\":{\"MsgId\":\"?0\"}}]}}")
    Optional<Log> findByMsgId(String MsgId);
    @Query("{\"bool\":{\"must\":[{\"match\":{\"MsgId\":\"?0\"}},{\"match\":{\"method\":\"?1\"}}]}}\n")
    Optional<Log> findByMsgIdAndMethod(String MsgId, String Method);
    @Query(" {\"bool\": { \"must\": [ { \"query_string\": { \"default_field\": \"message\", \"query\": \"?3\" }}, { \"bool\": { \"must\": [ { \"match\": { \"offset\": ?0  } }, { \"match\": {  \"method\": \"?2\"  } },{\"match\":{\"NodeId\": \"?1\"}} ] } } ] } }")
    List<Log> findByOffsetAndNodeIdAndMethodAndMessageContains(Long offSet, String nodeId, String method, String checkStr);
}
