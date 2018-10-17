package com.xinlu.respository.update;

import com.xinlu.model.Select_Body;
import com.xinlu.model.UpdateInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateJpaRepository extends JpaRepository<UpdateInfo, Long> {



}
