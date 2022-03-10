package com.springboot.online_bookstore_backend.repository;

import com.springboot.online_bookstore_backend.domain.Clickstream_Log;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository("clickstream_logDao")
public interface Clickstream_LogDao {
    void insert(Clickstream_Log clickstreamLog);
}
