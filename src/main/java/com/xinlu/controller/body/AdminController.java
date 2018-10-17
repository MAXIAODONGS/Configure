package com.xinlu.controller.body;

import com.xinlu.jpush.JpushClientUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Administration console controller.
 *
 * @author wangs
 */

@Controller
public class AdminController {



    @RequestMapping(value = "/sendToAll")
    @ResponseBody
    public int sendToAll() {
        return JpushClientUtil.sendToAll("test", "test", "test", "test");
    }
    @RequestMapping(value = "/sendToAllAndroid")
    @ResponseBody
    public int sendToAllAndroid() {
        return JpushClientUtil.sendToAllAndroid("test", "test", "test", "test");
    }
    @RequestMapping(value = "/sendToAllIos")
    @ResponseBody
    public int sendToAllIos() {
        return JpushClientUtil.sendToAllIos("test", "test", "test", "test");
    }


}