package org.deployment.StudentRegistration.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Course {

	private int courseId;
	private String courseCode;
	private String courseName;
	private String courseInformation;
	private String instructorName;
	private String courseSchedule;
	private int departmentId;
	
	public long getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getCourseInformation() {
		return courseInformation;
	}
	public void setCourseInformation(String courseInformation) {
		this.courseInformation = courseInformation;
	}
	public String getInstructorName() {
		return instructorName;
	}
	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}
	public String getCourseSchedule() {
		return courseSchedule;
	}
	public void setCourseSchedule(String courseSchedule) {
		this.courseSchedule = courseSchedule;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	
}
