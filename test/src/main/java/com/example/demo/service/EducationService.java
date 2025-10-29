package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Entity.EducationEntity;
import com.example.demo.Entity.ReportEntity;
import com.example.demo.repository.EducationRepository;
import com.example.demo.repository.ReportRepository;

@Service
public class EducationService {

    private final EducationRepository educationRepository;

    public EducationService(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    public List<EducationEntity> getAllEducation() {
        return educationRepository.findAll();
    }
}
