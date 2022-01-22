package pojo;

import java.util.ArrayList;
import java.util.List;

public class Courses {
	private List<CourseDescription> webAutomation = new ArrayList<CourseDescription>();
	private List<CourseDescription> api = new ArrayList<CourseDescription>();
	private List<CourseDescription> mobile = new ArrayList<CourseDescription>();
	
	public List<CourseDescription> getWebAutomation() {
		return webAutomation;
	}
	public void setWebAutomation(List<CourseDescription> webAutomation) {
		this.webAutomation = webAutomation;
	}
	public List<CourseDescription> getApi() {
		return api;
	}
	public void setApi(List<CourseDescription> api) {
		this.api = api;
	}
	public List<CourseDescription> getMobile() {
		return mobile;
	}
	public void setMobile(List<CourseDescription> mobile) {
		this.mobile = mobile;
	}
}