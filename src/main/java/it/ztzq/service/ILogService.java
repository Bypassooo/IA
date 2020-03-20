package it.ztzq.service;


import it.ztzq.domain.LogResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface ILogService {
    public Set<LogResult> compareLog(String version, String functionid, String serviceid, String method, String checkStr, Pageable pageable, String time);}
