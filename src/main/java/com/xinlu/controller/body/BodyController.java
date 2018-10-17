package com.xinlu.controller.body;


import com.xinlu.jpush.JpushClientUtil;
import com.xinlu.model.Select_Body;

import com.xinlu.service.body.IBodyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
@RequestMapping(value = "/body")
public class BodyController {
    @Autowired
    private IBodyService iBodyService;

    @RequestMapping(value = "/{id}")
    @ResponseBody
    public Select_Body getBody(@PathVariable Integer id) {
        return iBodyService.findOne(Long.valueOf(id));
    }


    @RequestMapping(value = "/add/{h_id}/{s_name}/{s_details}")
    public Select_Body addBody(@PathVariable int h_id,
                               @PathVariable String s_name, @PathVariable String s_details) {
        Select_Body select_body = new Select_Body();
        select_body.setH_id(h_id);
        select_body.setS_detail(s_details);
        select_body.setS_name(s_name);
        iBodyService.saveBody(select_body);
        return select_body;
    }

    @RequestMapping(value = "/delete/{id}")
    public void deleteBody(@PathVariable int id) {
        iBodyService.delete(id);
    }


    @RequestMapping(value = "/")
    @ResponseBody
    public List<Select_Body> getBody() {
        return iBodyService.findAll();
    }

    @RequestMapping(value = "/find/h_id/{h_id}")
    @ResponseBody
    public List<Select_Body> getBodyByHid(@PathVariable int h_id) {
        return iBodyService.findByHId(h_id);
    }

    @RequestMapping(value = "/find/page/{page}/{size}/{h_id}")
    @ResponseBody
    public Page<Select_Body> getBodyByHidPage(@PathVariable int h_id, @PathVariable Integer page,
                                              @PathVariable int size) {
        Pageable pageable = new PageRequest(page, size, new Sort(Sort.Direction.DESC, "id"));
        return iBodyService.find(h_id, pageable);
    }

    @RequestMapping(value = "/send/{content}/{platform}/{school}")
    @ResponseBody
    public int sendMessage(@PathVariable String content,@PathVariable String platform,@PathVariable String school) {



        return JpushClientUtil.sendToAll("test", "test", "test", "test");
    }
}
