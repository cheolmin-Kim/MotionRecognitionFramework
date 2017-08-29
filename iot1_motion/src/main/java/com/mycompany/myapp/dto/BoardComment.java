package com.mycompany.myapp.dto;

import java.util.Date;

public class BoardComment {
	private int bno;
	private int bcno;
	private String bcwriter;
	private String bcpassword;
	private Date bcdate;
	private String bccomment;
	private String mid;
	
	public String getBcpassword() {
		return bcpassword;
	}
	public void setBcpassword(String bcpassword) {
		this.bcpassword = bcpassword;
	}
	public int getBno() {
		return bno;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public int getBcno() {
		return bcno;
	}
	public void setBcno(int bcno) {
		this.bcno = bcno;
	}
	public String getBcwriter() {
		return bcwriter;
	}
	public void setBcwriter(String bcwriter) {
		this.bcwriter = bcwriter;
	}
	public Date getBcdate() {
		return bcdate;
	}
	public void setBcdate(Date bcdate) {
		this.bcdate = bcdate;
	}
	public String getBccomment() {
		return bccomment;
	}
	public void setBccomment(String bccomment) {
		this.bccomment = bccomment.replace("<", "&lt;");
		this.bccomment = bccomment.replace(">", "&gt;");
		this.bccomment = bccomment.replace("  ", "&nbsp;&nbsp;");
		this.bccomment = bccomment.replace("\n", "<br/>");
	}
}
