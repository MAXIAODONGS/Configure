package com.maxd.service.vpn;

import com.maxd.model.VpnInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IVpnService {

    List<VpnInfo> findAll();


    void saveBody(VpnInfo vpnInfo);


    VpnInfo findOne(long id);


    void delete(long id);


    List<VpnInfo> findByOu(String ou);

    Page<VpnInfo> find(String ou, Pageable pageable);

    void updateBody(VpnInfo vpnInfo);
}
