package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Entity.ReportEntity;
import com.example.demo.repository.ReportRepository;

@Service
public class ReportService {

	private final ReportRepository reportRepository;

	public ReportService(ReportRepository reportRepository) {
		this.reportRepository = reportRepository;
	}

	public List<ReportEntity> getAllReports() {
		return reportRepository.findAll();
	}
	
	// =====================================================
	
	
}
