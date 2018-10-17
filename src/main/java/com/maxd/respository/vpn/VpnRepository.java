package com.maxd.respository.vpn;


import com.maxd.model.VpnInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VpnRepository extends Repository<VpnInfo, Long> {
    @Query(value = "from VpnInfo s where s.schoolId=:schoolId order by id desc")
    List<VpnInfo> findById(@Param("schoolId") String schoolId);

    @Query(value = "from VpnInfo s where s.schoolId=:schoolId ")
    Page<VpnInfo> find(@Param("schoolId") String schoolId, Pageable pageable);

}