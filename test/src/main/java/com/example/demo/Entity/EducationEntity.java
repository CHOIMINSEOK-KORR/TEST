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

    
	
	
}
