package com.maxd.service.vpn;

import com.maxd.model.VpnInfo;
import com.maxd.respository.vpn.VpnJpaRepository;
import com.maxd.respository.vpn.VpnRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 轮播图的service实现类
 */
@Service
public class VpnServiceImpl implements IVpnService {
    @Autowired
    private VpnJpaRepository vpnJpaRepository;
    @Autowired
    private VpnRepository vpnRepository;

    @Override
    public List<VpnInfo> findAll() {
        return vpnJpaRepository.findAll();
    }

    @Override
    public void saveBody(VpnInfo vpnInfo) {
        vpnJpaRepository.save(vpnInfo);
    }


    @Override
    public VpnInfo findOne(long id) {
        return vpnJpaRepository.findOne(id);
    }

    @Override
    public void delete(long id) {
        vpnJpaRepository.delete(id);
    }


    @Override
    public List<VpnInfo> findByOu(String ou) {
        return vpnRepository.findById(ou);
    }

    @Override
    public Page<VpnInfo> find(String app, Pageable pageable) {
        return vpnRepository.find(app, pageable);
    }


    @Override
    @Transactional
    public void updateBody(VpnInfo vpnInfo) {
        VpnInfo vpnInfo1 = vpnJpaRepository.findOne(vpnInfo.getId());
        BeanUtils.copyProperties(vpnInfo, vpnInfo1);
    }


}
