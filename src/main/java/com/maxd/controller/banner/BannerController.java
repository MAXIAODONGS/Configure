package com.maxd.controller.banner;


import com.maxd.model.*;
import com.maxd.service.banner.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/banner")
public class BannerController {
    @Autowired
    private IBannerService iBannerService;

    @RequestMapping(value = "/{id}")
    @ResponseBody
    public Banner getBanner(@PathVariable Integer id) {
        return iBannerService.findOne(Long.valueOf(id));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addBanner(@RequestBody Banner banner) {
        return iBannerService.addBannerInfo(banner);

    }

    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    @ResponseBody
    public void uUpdate(@RequestBody Banner banner) {
        iBannerService.saveBody(banner);
    }
    @RequestMapping(value = "/delete/{id}")
    public void deleteBanner(@PathVariable int id) {
        iBannerService.delete(id);
    }

    @RequestMapping(value = "/delete/all")
    public void deleteAllBanner() {
        List<Banner> all = iBannerService.findAll();
        for (Banner b : all) {
            iBannerService.delete(b.getId());
        }
    }

    @RequestMapping(value = "/")
    @ResponseBody
    public List<Banner> getBannerList() {
        return iBannerService.findAll();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Banner updateBanner(@RequestBody Banner banner) {
        iBannerService.updateBody(banner);
        return banner;
    }

    @RequestMapping(value = "/add/all", method = RequestMethod.POST)
    @ResponseBody
    public String addAllBanner(@RequestBody List<ImageBanner> imageBanners) {
        return iBannerService.addAllBanner(imageBanners);
    }

    @RequestMapping(value = "/out", method = RequestMethod.GET)
    @ResponseBody
    public List<ImageBanner> findOutList() {
        return iBannerService.findOutList();

    }

    @RequestMapping(value = "/list/{ou}")
    @ResponseBody
    public List<Banner> getBodyByOu(@PathVariable String ou) {
        return iBannerService.getBodyByOu(ou);
    }

    @RequestMapping(value = "/server/write", method = RequestMethod.GET)
    @ResponseBody
    public String writeBodyInfoToServer() {
        return iBannerService.writeBodyInfoToServer();
    }

    @RequestMapping(value = "/server/read", method = RequestMethod.GET)
    @ResponseBody
    public String getBodyInfoFromServer() {
        return iBannerService.getBodyInfoFromServer();
    }
}
