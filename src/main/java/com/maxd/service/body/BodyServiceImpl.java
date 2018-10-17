package com.maxd.service.body;

import com.maxd.model.Select_Body;
import com.maxd.respository.body.BodyJpaRepository;
import com.maxd.respository.body.BodyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BodyServiceImpl implements IBodyService {
    @Autowired
    private BodyJpaRepository bodyJpaRepository;
    @Autowired
    private BodyRepository bodyRepository;

    @Override
    public List<Select_Body> findAll() {
        return bodyJpaRepository.findAll();
    }

    @Override
    public void saveBody(Select_Body select_body) {
        bodyJpaRepository.save(select_body);

    }

    @Override
    public Select_Body findOne(long id) {
        return bodyJpaRepository.findOne(id);
    }

    @Override
    public void delete(long id) {
        bodyJpaRepository.delete(id);

    }

    @Override
    public List<Select_Body> findByHId(int h_id) {

        return bodyRepository.findById(h_id);
    }

    @Override
    public Page<Select_Body> find(int h_id, Pageable pageable) {
        return bodyRepository.find(h_id, pageable);
    }

}
