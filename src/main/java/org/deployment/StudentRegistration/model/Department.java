package org.deployment.StudentRegistration.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Department {

	private int departmentId;
	private String departmentName;
	private String departmentInformation;
	
	public String getDepartmentInformation() {
		return departmentInformation;
	}
	public void setDepartmentInformation(String departmentInformation) {
		this.departmentInformation = departmentInformation;
	}
	public long getDepartmentId() {
		return departmentId;  
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
}
