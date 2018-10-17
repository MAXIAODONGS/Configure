package com.xinlu.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Select_Body implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column()
	private String s_name;
	@Column()
	private String s_detail;
	@Column()
	private int h_id;
	public Select_Body() {

	}
	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getS_name() {
		return s_name;
	}

	public void setS_name(String s_name) {
		this.s_name = s_name;
	}

	public String getS_detail() {
		return s_detail;
	}

	public void setS_detail(String s_detail) {
		this.s_detail = s_detail;
	}

	public int getH_id() {
		return h_id;
	}

	public void setH_id(int h_id) {
		this.h_id = h_id;
	}

}
