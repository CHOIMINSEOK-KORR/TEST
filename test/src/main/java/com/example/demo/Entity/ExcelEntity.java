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

}
