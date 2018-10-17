package com.xinlu.controller.alert;

import com.xinlu.model.*;
import com.xinlu.service.alert.IAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/alert")
public class AlertController {
    @Autowired
    private IAlertService iAlertService;
    private List<OutAlert> alerts;

    @RequestMapping(value = "/{id}")
    @ResponseBody
    public Alert getAlert(@PathVariable Integer id) {
        return iAlertService.findOne(Long.valueOf(id));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addAlert(@RequestBody Alert alert) {
        return iAlertService.addAlertInfo(alert);
    }

    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    @ResponseBody
    public void uUpdate(@RequestBody Alert alert) {
        iAlertService.saveBody(alert);
    }

    @RequestMapping(value = "/delete/{id}")
    public void deleteAlert(@PathVariable int id) {
        iAlertService.delete(id);
    }

    @RequestMapping(value = "/delete/all")
    public void deleteAllAlert() {

        iAlertService.deleteAllAlert();
    }

    @RequestMapping(value = "/")
    @ResponseBody
    public List<Alert> getAlertList() {
        return iAlertService.findAll();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Alert updateAlert(@RequestBody Alert alert) {
        iAlertService.updateBody(alert);
        return alert;
    }

    @RequestMapping(value = "/add/all", method = RequestMethod.POST)
    @ResponseBody
    public String addAllAlert(@RequestBody List<OutAlert> outAlerts) {
        return iAlertService.addAllAlert(outAlerts);
    }

    @RequestMapping(value = "/out", method = RequestMethod.GET)
    @ResponseBody
    public List<OutAlert> findOutList() {
        return iAlertService.findOutList();
    }

    @RequestMapping(value = "/list/{ou}", method = RequestMethod.GET)
    @ResponseBody
    public List<Alert> getBodyByOu(@PathVariable String ou) {
        return iAlertService.getBodyByOu(ou);

    }

    @RequestMapping(value = "/server/write", method = RequestMethod.GET)
    @ResponseBody
    public String writeBodyInfoToServer() {
        return iAlertService.writeBodyInfoToServer();
    }

    @RequestMapping(value = "/server/read", method = RequestMethod.GET)
    @ResponseBody
    public String getBodyInfoFromServer() {
        return iAlertService.getBodyInfoFromServer();
    }
}
