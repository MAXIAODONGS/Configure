package com.xinlu.service.update;

import com.xinlu.model.Select_Body;
import com.xinlu.model.UpdateInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUpdateService {
    List<UpdateInfo> findAll();

    void saveBody(UpdateInfo updateInfo);

    UpdateInfo findOne(long id);

    void delete(long id);

    List<UpdateInfo> findByApp(String app);

    Page<UpdateInfo> find(String app, Pageable pageable);

    void updateBody(UpdateInfo updateInfo);
}
