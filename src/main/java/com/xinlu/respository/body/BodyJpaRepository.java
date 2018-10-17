package com.xinlu.respository.body;

import com.xinlu.model.Select_Body;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyJpaRepository extends JpaRepository<Select_Body, Long> {



}
