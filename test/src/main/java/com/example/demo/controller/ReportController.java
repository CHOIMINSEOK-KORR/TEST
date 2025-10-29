package com.example.demo.controller;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.example.demo.Entity.EducationEntity;
import com.example.demo.Entity.ReportEntity;
import com.example.demo.service.EducationService;
import com.example.demo.service.ReportService;
import com.lowagie.text.pdf.BaseFont;

import jakarta.servlet.ServletContext;

@Controller
@RequestMapping("/test")
public class ReportController {

    private final ReportService reportService;
    private final TemplateEngine templateEngine;
    private final ServletContext servletContext;
    private final EducationService educationService;

    public ReportController(ReportService reportService, TemplateEngine templateEngine, ServletContext servletContext, EducationService educationService) {
        this.reportService = reportService;
        this.templateEngine = templateEngine;
        this.servletContext = servletContext;
        this.educationService = educationService;
    }
    
    @GetMapping("/report/pdf")
    public ResponseEntity<byte[]> generatePdf(Model model) throws Exception {
    	
    	int reportPageSize = 28;
    	int eduPageSize = 10;
    	
    	
        List<ReportEntity> reports = reportService.getAllReports();
        List<EducationEntity> educations = educationService.getAllEducation();
        
//        List<List<EducationEntity>> educationPages = new ArrayList<>();
//        List<EducationEntity> currentPage = new ArrayList<>();
        
        
        // 1) reports 페이지 나누기
        List<List<ReportEntity>> reportPages = new ArrayList<>();
        for (int i = 0; i < reports.size(); i += reportPageSize) {
        	int end = Math.min(i + reportPageSize, reports.size());
        	reportPages.add(reports.subList(i, end));
        }
//        
        // 2) educations 페이지 나누기
        List<List<EducationEntity>> educationPages = new ArrayList<>();
        for (int i = 0; i < educations.size(); i += eduPageSize) {
        	int end = Math.min(i + eduPageSize, educations.size());
        	educationPages.add(educations.subList(i, end));
        }

        // Thymeleaf 바인딩해서 HTML 생성
        Context context = new Context();
        context.setVariable("reports", reports);
        context.setVariable("educations", educations);
//        context.setVariable("reportPages", reportPages);
//        context.setVariable("educationPages", educationPages);
        
        String htmlContent = templateEngine.process("report", context);

        // PDF 생성
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();

        // 한글 폰트 등록 (TTF 사용)
        String fontPath = "C:/Windows/Fonts/gulim.ttc";
        renderer.getFontResolver().addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(baos);
        renderer.finishPDF();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "report.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(baos.toByteArray());
    }
}