package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.demo.Entity.EducationEntity;
import com.example.demo.Entity.ReportEntity;
import com.example.demo.service.ReportService2;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

@Controller
@RequestMapping("/test")
public class ReportController2 {

    private final ReportService2 reportService;
    private final TemplateEngine templateEngine;

    public ReportController2(ReportService2 reportService, TemplateEngine templateEngine) {
        this.reportService = reportService;
        this.templateEngine = templateEngine;
    }

    @GetMapping("/report/pdf/final")
    public ResponseEntity<InputStreamResource> generatePdf(Model model) throws Exception {
    	
    	// 필요한 데이터 리스트로 들고오기
        List<ReportEntity> reports = reportService.getAllReports();
        List<EducationEntity> educations = reportService.getAllEducation();

        // Thymeleaf에 렌더링할 데이터값들 context에 전달
        Context context = new Context();
        context.setVariable("reports", reports);
        context.setVariable("educations", educations);

        
        // pdf변환기는 html문자열을 요구하기에 직접 타임리프를 돌려서 렌더링 해야함
        String htmlContent = templateEngine.process("report2", context);  

        // 메모리 버퍼에 저장하기 위해서 ByteArrayOutputStream 객체 사용
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfRendererBuilder builder = new PdfRendererBuilder();
        
        // pdf변환하는 과정에서 html문자열과 baseURI 전달 메서드(지금은 이미지가 필요없어서 null로 줌)
        builder.withHtmlContent(htmlContent, null);
        
        // 폰트 추가 NotoSansKR << 얘도 추가해야지 특수문자 나옴
        builder.useFont(() -> getClass().getResourceAsStream("/fonts/NanumGothic-Regular.ttf"), "NanumGothic");
        builder.useFont(() -> getClass().getResourceAsStream("/fonts/NotoSansKR-VariableFont_wght.ttf"), "NotoSansKR");
        
        // pdf에 바이트 채우기
        builder.toStream(outputStream);
        
        // PDF로 변환
        builder.run();

        // PDF 응답 반환
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        InputStreamResource resource = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
        		// Content-Disposition: inline(새 탭에서 pdf 열림)
        		//Content-Disposition: attachment(pdf 다운로드)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}
