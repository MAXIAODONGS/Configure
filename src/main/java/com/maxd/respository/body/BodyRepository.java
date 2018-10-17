package com.maxd.respository.body;

import com.maxd.model.Select_Body;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BodyRepository extends Repository<Select_Body, Long> {
    @Query(value = "from Select_Body s where s.h_id=:h_id")
    List<Select_Body> findById(@Param("h_id") int h_id);

    @Query(value = "from Select_Body s where s.h_id=:h_id ")
    Page<Select_Body> find(@Param("h_id") int h_id,Pageable pageable);

}