package org.deployment.StudentRegistration.bean;

import javax.ws.rs.QueryParam;

public class MessageFilterBean {

	private @QueryParam("country") String country;
	private @QueryParam("start") int start;
	private @QueryParam("size") int size;
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	
	
}
