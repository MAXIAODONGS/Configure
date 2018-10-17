package com.maxd.respository.body;

import com.maxd.model.Select_Body;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyJpaRepository extends JpaRepository<Select_Body, Long> {



}
