package com.xinlu.respository.banner;

import com.xinlu.model.Banner;
import com.xinlu.model.Select_Body;
import com.xinlu.model.UpdateInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BannerRepository extends Repository<Banner, Long> {
    @Query(value = "from Banner s where s.ou=:ou order by id desc")
    List<Banner> findById(@Param("ou") String ou);

    @Query(value = "from Banner s where s.ou=:ou ")
    Page<Banner> find(@Param("ou") String ou, Pageable pageable);

    @Query(value = "select s.ou from Banner s group by ou")
    List<String> findOu();

}