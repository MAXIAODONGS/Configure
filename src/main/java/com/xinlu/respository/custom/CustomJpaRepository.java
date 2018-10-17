package com.xinlu.respository.custom;

import com.xinlu.model.Custom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomJpaRepository extends JpaRepository<Custom, Long> {



}
