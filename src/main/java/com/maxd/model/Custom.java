package com.maxd.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Custom  implements Serializable {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String schoolId;
    @Column
    private String imageUrl;
    @Column
    private String name;
    @Column
    private String time;
    @Column
    private String schoolName;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name1) {
        name = name1;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
