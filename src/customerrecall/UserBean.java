package customerrecall;

import java.sql.Date;

public class UserBean {
	
	String cname;
	String mobile;
	String address;
	String area;
	String hawker;
	String selpaper;
	Date dos;
	float bill;
	public UserBean() {}
	public UserBean(String cname, String mobile, String address, String area, String hawker, Date dos, String selpaper,
			float bill) {
		super();
		this.cname = cname;
		this.mobile = mobile;
		this.address = address;
		this.area = area;
		this.hawker = hawker;
		this.selpaper = selpaper;
		this.dos = dos;
		this.bill = bill;
	}
	
	
	
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getHawker() {
		return hawker;
	}
	public void setHawker(String hawker) {
		this.hawker = hawker;
	}
	public String getSelpaper() {
		return selpaper;
	}
	public void setSelpaper(String selpaper) {
		this.selpaper = selpaper;
	}
	public Date getDos() {
		return dos;
	}
	public void setDos(Date dos) {
		this.dos = dos;
	}
	public float getBill() {
		return bill;
	}
	public void setBill(float bill) {
		this.bill = bill;
	}
	
	

}
