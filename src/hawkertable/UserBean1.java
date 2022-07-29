package hawkertable;

import java.sql.Date;

public class UserBean1 {

	//fields name same as table columns name
	//must have contructors and gettters and setters
		
	String hname;
	String address;
	String mobile;
	Date doj;
	public UserBean1() {} //default cons manually
	public UserBean1(String hname, String address, String mobile, Date doj) {
		super();
		this.hname = hname;
		this.address = address;
		this.mobile = mobile;
		this.doj = doj;
	}
	public String getHname() {
		return hname;
	}
	public void setHname(String hname) {
		this.hname = hname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Date getDoj() {
		return doj;
	}
	public void setDoj(Date doj) {
		this.doj = doj;
	}
	
}
