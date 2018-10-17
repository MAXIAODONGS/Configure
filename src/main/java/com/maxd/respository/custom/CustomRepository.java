package com.maxd.respository.custom;

import com.maxd.model.Custom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomRepository extends Repository<Custom, Long> {
    @Query(value = "from Custom s where s.schoolId=:schoolId order by id desc")
    List<Custom> findById(@Param("schoolId") String schoolId);

    @Query(value = "from Custom s where s.schoolId=:schoolId ")
    Page<Custom> find(@Param("schoolId") String schoolId, Pageable pageable);

}