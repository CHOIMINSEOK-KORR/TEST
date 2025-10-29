package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.PdfService;

import jakarta.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/test")
public class PdfController {

	// PdfService 의존성 주입
	private final PdfService pdfService;
	
	public PdfController(PdfService pdfService) {
		this.pdfService = pdfService;
	}


    @GetMapping("/download/pdf/{id}")
    public void downloadPdf(@PathVariable("id") Long id, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        String fileName = "Certificate_" + id + ".pdf";
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        pdfService.generateCertificatePdf(id, response.getOutputStream());
    }
	
}
