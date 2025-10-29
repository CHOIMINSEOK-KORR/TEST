package com.example.demo.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Excel")
public class ExcelEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "student_name", length = 50)
	private String studentName;
	
	@Column(name = "birthday", length = 50)
	private String birthday;
	
	@Column(name = "progress_rate", length = 500)
	private String progressRate;
	
	@Column(name = "midterm_evaluation")
	private Integer midtermEvaluation;
	
	@Column(name = "final_exam")
	private Integer finalExam;
	
	@Column(name = "total_score")
	private Integer totalScore;
	
	@Column(name = "completion_status", length = 50)
	private String completionStatus;

	
	// getter
	public Long getId() {
		return id;
	}

	public String getStudentName() {
		return studentName;
	}

	public String getBirthday() {
		return birthday;
	}

	public String getProgressRate() {
		return progressRate;
	}

	public Integer getMidtermEvaluation() {
		return midtermEvaluation;
	}

	public Integer getFinalExam() {
		return finalExam;
	}

	public Integer getTotalScore() {
		return totalScore;
	}

	public String getCompletionStatus() {
		return completionStatus;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
	// setter
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	public void setProgressRate(String progressRate) {
		this.progressRate = progressRate;
	}
	
	public void setMidtermEvaluation(Integer midtermEvaluation) {
		this.midtermEvaluation = midtermEvaluation;
	}
	
	public void setFinalExam(Integer finalExam) {
		this.finalExam = finalExam;
	}
	
	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}
	
	public void setCompletionStatus(String completionStatus) {
		this.completionStatus = completionStatus;
	}
	
	
}
