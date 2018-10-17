package com.maxd.respository.update;

import com.maxd.model.UpdateInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateJpaRepository extends JpaRepository<UpdateInfo, Long> {



}
