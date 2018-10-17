package com.xinlu.controller.custom;

import com.xinlu.utils.Utils;
import com.xinlu.model.*;
import com.xinlu.service.custom.ICustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/custom")
public class CustomController {
    @Autowired
    private ICustomService iCustomService;
    private ArrayList<OutCustom> customs;

    @RequestMapping(value = "/{id}")
    @ResponseBody
    public Custom getCustomInfo(@PathVariable Integer id) {
        return iCustomService.findOne(Long.valueOf(id));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addCustomInfo(@RequestBody Custom custom) {
        return iCustomService.addCustomInfo(custom);

    }

    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    @ResponseBody
    public void uUpdate(@RequestBody Custom custom) {
        iCustomService.saveBody(custom);
    }
    @RequestMapping(value = "/delete/all")
    public void deleteAllCustom() {
        iCustomService.deleteAllCustom();
    }

    @RequestMapping(value = "/delete/{id}")
    public void deleteCustom(@PathVariable int id) {
        iCustomService.delete(id);
    }

    @RequestMapping(value = "/")
    @ResponseBody
    public List<Custom> getCustomList() {
        return iCustomService.findAll();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Custom updateCustom(@RequestBody Custom custom) {
        iCustomService.updateBody(custom);
        return custom;
    }

    @RequestMapping(value = "/out", method = RequestMethod.GET)
    @ResponseBody
    public List<OutCustom> findOutList() {
        return iCustomService.findOutList();
    }

    @RequestMapping(value = "/add/all", method = RequestMethod.POST)
    @ResponseBody
    public String addAllCustom(@RequestBody List<OutCustom> customs) {
        return iCustomService.addAllCustom(customs);
    }

    @RequestMapping(value = "/list/{ou}")
    @ResponseBody
    public List<Custom> getBodyByOu(@PathVariable String ou) {
        return iCustomService.getBodyByOu(ou);
    }


    @RequestMapping(value = "/server/write", method = RequestMethod.GET)
    @ResponseBody
    public String writeBodyInfoToServer() {
        return iCustomService.writeBodyInfoToServer();
    }

    @RequestMapping(value = "/server/read", method = RequestMethod.GET)
    @ResponseBody
    public String getBodyInfoFromServer() {
        return iCustomService.getBodyInfoFromServer();
    }
}
