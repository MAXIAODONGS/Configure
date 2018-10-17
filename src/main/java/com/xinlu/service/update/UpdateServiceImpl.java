package com.xinlu.service.update;

import com.xinlu.model.UpdateInfo;
import com.xinlu.respository.update.UpdateJpaRepository;
import com.xinlu.respository.update.UpdateRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UpdateServiceImpl implements IUpdateService {
    @Autowired
    private UpdateJpaRepository updateJpaRepository;
    @Autowired
    private UpdateRepository bodyRepository;
    @Override
    public List<UpdateInfo> findAll() {
        return updateJpaRepository.findAll();
    }
    @Override
    public void saveBody(UpdateInfo select_body) {
        updateJpaRepository.save(select_body);
    }
    @Override
    public UpdateInfo findOne(long id) {
        return updateJpaRepository.findOne(id);
    }
    @Override
    public void delete(long id) {
        updateJpaRepository.delete(id);
    }
    @Override
    public List<UpdateInfo> findByApp(String app) {
        return bodyRepository.findById(app);
    }
    @Override
    public Page<UpdateInfo> find(String app, Pageable pageable) {
        return bodyRepository.find(app, pageable);
    }
    @Override
    @Transactional
    public void updateBody(UpdateInfo updateInfo) {
        UpdateInfo update = updateJpaRepository.findOne(updateInfo.getId());
        BeanUtils.copyProperties(updateInfo, update);
    }


}
