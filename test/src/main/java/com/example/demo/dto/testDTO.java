package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class testDTO {
	private Long id;
	private String studentName;
	private String courseTitle;
	private String completionDate;
	
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getStudentName() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getCourseTitle() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getCompletionDate() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public void setCompletionDate(String completionDate) {
		this.completionDate = completionDate;
	}
	

}
