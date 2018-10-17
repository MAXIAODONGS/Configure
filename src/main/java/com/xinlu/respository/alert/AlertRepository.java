package com.xinlu.respository.alert;

import com.xinlu.model.Alert;
import com.xinlu.model.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlertRepository extends Repository<Alert, Long> {
    @Query(value = "from Alert s where s.schoolId=:schoolId order by id desc ")
    List<Alert> findById(@Param("schoolId") String schoolId);



    @Query(value = "from Alert s where s.schoolId=:schoolId ")
    Page<Alert> find(@Param("schoolId") String schoolId, Pageable pageable);

}