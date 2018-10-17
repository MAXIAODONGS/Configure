package com.maxd.respository.banner;

import com.maxd.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerJpaRepository extends JpaRepository<Banner, Long> {



}
