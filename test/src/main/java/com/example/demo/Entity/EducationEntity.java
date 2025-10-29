package com.example.demo.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "education")
public class EducationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "session", length = 50)
	private String session;
	
	@Column(name = "training_title", length = 100)
	private String trainingTitle;
	
	@Column(name = "training_content", length = 500)
	private String trainingContent;

    
    // getter
	public Long getId() {
		return id;
	}

	public String getSession() {
		return session;
	}

	public String getTrainingTitle() {
		return trainingTitle;
	}

	public String getTrainingContent() {
		return trainingContent;
	}

	
	// setter
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setSession(String session) {
		this.session = session;
	}
	
	public void setTrainingTitle(String trainingTitle) {
		this.trainingTitle = trainingTitle;
	}
	
	public void setTrainingContent(String trainingContent) {
		this.trainingContent = trainingContent;
	}
	
	
}
