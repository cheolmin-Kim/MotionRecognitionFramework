package com.mycompany.myapp.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class Board {
	private int bno;
	private String btitle;
	private String bcontent;
	private String bwriter;
	private Date bdate;
	private String bpassword;
	private int bhitcount;
	private int blikecount;
	private String boriginalfilename;
	private String bsavedfilename;
	private String bfilecontent;
	private String mid;
	MultipartFile battach;

	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public MultipartFile getBattach() {
		return battach;
	}

	public void setBattach(MultipartFile battach) {
		this.battach = battach;
	}

	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public String getBtitle() {
		return btitle;
	}

	public void setBtitle(String btitle) {
		this.btitle = btitle;
	}

	public String getBcontent() {
		return bcontent;
	}

	public void setBcontent(String bcontent) {
		this.bcontent = bcontent;
	}

	public String getBwriter() {
		return bwriter;
	}

	public void setBwriter(String bwriter) {
		this.bwriter = bwriter;
	}

	public Date getBdate() {
		return bdate;
	}

	public void setBdate(Date bdate) {
		this.bdate = bdate;
	}

	public String getBpassword() {
		return bpassword;
	}

	public void setBpassword(String bpassword) {
		this.bpassword = bpassword;
	}

	public int getBhitcount() {
		return bhitcount;
	}

	public void setBhitcount(int bhitcount) {
		this.bhitcount = bhitcount;
	}

	public int getBlikecount() {
		return blikecount;
	}

	public void setBlikecount(int blikecount) {
		this.blikecount = blikecount;
	}

	public String getBoriginalfilename() {
		return boriginalfilename;
	}

	public void setBoriginalfilename(String boriginalfilename) {
		this.boriginalfilename = boriginalfilename;
	}

	public String getBsavedfilename() {
		return bsavedfilename;
	}

	public void setBsavedfilename(String bsavedfilename) {
		this.bsavedfilename = bsavedfilename;
	}

	public String getBfilecontent() {
		return bfilecontent;
	}

	public void setBfilecontent(String bfilecontent) {
		this.bfilecontent = bfilecontent;
	}
}
