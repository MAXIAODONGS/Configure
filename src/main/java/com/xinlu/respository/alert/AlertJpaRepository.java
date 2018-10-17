package com.xinlu.respository.alert;

import com.xinlu.model.Alert;
import com.xinlu.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertJpaRepository extends JpaRepository<Alert, Long> {



}
