package pojo;

import java.util.List;

public class Courses {
	private List<CourseDetails> CourseDetails;
	private List<CourseDetails> api;
	private List<CourseDetails> mobile;
	public List<CourseDetails> getCourse() {
		return CourseDetails;
	}
	public void setCourse(List<CourseDetails> courseDetails) {
		CourseDetails = courseDetails;
	}
	public List<CourseDetails> getApi() {
		return api;
	}
	public void setApi(List<CourseDetails> api) {
		this.api = api;
	}
	public List<CourseDetails> getMobile() {
		return mobile;
	}
	public void setMobile(List<CourseDetails> mobile) {
		this.mobile = mobile;
	}
}