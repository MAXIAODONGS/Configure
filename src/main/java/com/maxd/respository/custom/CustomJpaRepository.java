package com.maxd.respository.custom;

import com.maxd.model.Custom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomJpaRepository extends JpaRepository<Custom, Long> {



}
