package com.maxd.respository.alert;

import com.maxd.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertJpaRepository extends JpaRepository<Alert, Long> {



}
