package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Entity.EducationEntity;
import com.example.demo.Entity.ReportEntity;
import com.example.demo.repository.EducationRepository;
import com.example.demo.repository.ReportRepository2;

@Service
@Transactional
public class ReportService2 {

    private final ReportRepository2 reportRepository;
    private final EducationRepository educationRepository;

    public ReportService2(ReportRepository2 reportRepository, EducationRepository educationRepository) {
        this.reportRepository = reportRepository;
        this.educationRepository = educationRepository;
    }

    public List<ReportEntity> getAllReports() {
        return reportRepository.findAll();
    }

    public List<EducationEntity> getAllEducation() {
        return educationRepository.findAll();
    }
}
