package com.maxd.model;

import java.util.List;

/**
 * 作者 Created by maxd
 * 邮箱 maxd@nroad.com.cn
 * 时间 on 2018/9/20 0020
 */

public class ImageBanner {


    /**
     * image_banner : [{"image_url":"https://api.wenet.com.cn/product_images/img/image-1.png","pid":"false","token":"true","url":"https://api.wenet.com.cn/product_images/img/image-1.png"},{"image_url":"https://api.wenet.com.cn/product_images/img/image-2.png","pid":"false","token":"true","url":"https://api.wenet.com.cn/product_images/img/image-2.png"},{"image_url":"https://api.wenet.com.cn/product_images/img/image-3.png","pid":"false","token":"true","url":"https://api.wenet.com.cn/product_images/img/image-3.png"},{"image_url":"https://api.wenet.com.cn/product_images/img/image-4.png","pid":"false","token":"true","url":"https://api.wenet.com.cn/product_images/img/image-4.png"}]
     * ou : 10704
     */

    private String ou;
    private List<ImageBannerBean> image_banner;

    public String getOu() {
        return ou;
    }

    public void setOu(String ou) {
        this.ou = ou;
    }

    public List<ImageBannerBean> getImage_banner() {
        return image_banner;
    }

    public void setImage_banner(List<ImageBannerBean> image_banner) {
        this.image_banner = image_banner;
    }

    public static class ImageBannerBean {
        /**
         * image_url : https://api.wenet.com.cn/product_images/img/image-1.png
         * pid : false
         * token : true
         * url : https://api.wenet.com.cn/product_images/img/image-1.png
         */

        private String image_url;
        private String pid;
        private String token;
        private String url;

        public ImageBannerBean() {
        }

        public ImageBannerBean(String image_url, String pid, String token, String url) {
            this.image_url = image_url;
            this.pid = pid;
            this.token = token;
            this.url = url;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
