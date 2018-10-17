package com.maxd.service.body;

import com.maxd.model.Select_Body;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
public interface IBodyService  {
    List<Select_Body> findAll();

    void saveBody(Select_Body select_body);

    Select_Body findOne(long id);

    void delete(long id);

    List<Select_Body> findByHId(int h_id);

    Page<Select_Body> find(int h_id, Pageable pageable);

}
