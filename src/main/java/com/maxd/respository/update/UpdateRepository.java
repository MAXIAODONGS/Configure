package com.maxd.respository.update;

import com.maxd.model.Select_Body;
import com.maxd.model.UpdateInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UpdateRepository extends Repository<Select_Body, Long> {
    @Query(value = "from UpdateInfo s where s.app=:app order by id desc")
    List<UpdateInfo> findById(@Param("app") String app);

    @Query(value = "from UpdateInfo s where s.app=:app ")
    Page<UpdateInfo> find(@Param("app") String app, Pageable pageable);

}