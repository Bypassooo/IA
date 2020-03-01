package it.ztzq.repositories;

import it.ztzq.domain.Rmsg;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface RmsgRepository extends ElasticsearchRepository<Rmsg,String> {
    @Query("{\"bool\":{\"must\":[{\"match\":{\"OrgMsgid\":\"?0\"}}]}}")
    Optional<Rmsg> findByOrgMsgid(String OrgMsgid);
}
