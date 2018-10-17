package com.maxd.respository.vpn;

import com.maxd.model.VpnInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VpnJpaRepository extends JpaRepository<VpnInfo, Long> {



}
