package com.xinlu.respository.vpn;

import com.xinlu.model.Alert;
import com.xinlu.model.VpnInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VpnJpaRepository extends JpaRepository<VpnInfo, Long> {



}
