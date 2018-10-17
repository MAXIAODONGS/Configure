package com.xinlu.model;

import javax.persistence.*;
import java.io.Serializable;


@Entity
public class Banner  implements Serializable {
    /**
     * image_banner : [{"image_url":"https://api.wenet.com.cn/product_images/img/image-1.png","pid":"false","token":"true","url":"https://api.wenet.com.cn/product_images/img/image-1.png"},{"image_url":"https://api.wenet.com.cn/product_images/img/image-2.png","pid":"false","token":"true","url":"https://api.wenet.com.cn/product_images/img/image-2.png"},{"image_url":"https://api.wenet.com.cn/product_images/img/image-3.png","pid":"false","token":"true","url":"https://api.wenet.com.cn/product_images/img/image-3.png"},{"image_url":"https://api.wenet.com.cn/product_images/img/image-4.png","pid":"false","token":"true","url":"https://api.wenet.com.cn/product_images/img/image-4.png"}]
     * ou : 10704
     */
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String time;
    @Column
    private String ou;
    @Column
    private String image_url;
    @Column
    private String pid;
    @Column
    private String token;
    @Column
    private String url;
    @Column
    private String schoolName;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOu() {
        return ou;
    }

    public void setOu(String ou) {
        this.ou = ou;
    }


}
