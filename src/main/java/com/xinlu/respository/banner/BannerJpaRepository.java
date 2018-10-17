package com.xinlu.respository.banner;

import com.xinlu.model.Banner;
import com.xinlu.model.UpdateInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerJpaRepository extends JpaRepository<Banner, Long> {



}
