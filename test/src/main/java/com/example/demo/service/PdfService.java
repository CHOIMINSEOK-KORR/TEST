package com.example.demo.service;

import com.example.demo.Entity.ExcelEntity;
import com.example.demo.repository.ExcelRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.Optional;

@Service
public class PdfService {

    private final ExcelRepository enrollmentRepository;
    private final SpringTemplateEngine templateEngine;
    private final ResourceLoader resourceLoader; // 파일 경로가 classpath, 파일 시스템, URL 등 어디에 있든 상관없이, 일관된 방식으로 리소스를 읽을 수 있게 해주는 도구

    public PdfService(ExcelRepository enrollmentRepository, SpringTemplateEngine templateEngine, ResourceLoader resourceLoader) {
        this.enrollmentRepository = enrollmentRepository;
        this.templateEngine = templateEngine;
        this.resourceLoader = resourceLoader;
    }

     // 수료증 PDF 생성
    public void generateCertificatePdf(Long enrollmentId, OutputStream outputStream) {
        ExcelEntity entity = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("ID " + enrollmentId + "에 해당하는 수강생 정보를 찾을 수 없습니다."));

        try {
            // 1️⃣ HTML 렌더링
            Context context = new Context();
            context.setVariable("title", "수 료 증");
            context.setVariable("entity", entity);

            // 2️⃣ Flying Saucer PDF 변환
            ITextRenderer renderer = new ITextRenderer();
            String logourl = resourceLoader.getResource("classpath:/images/").getURL().toString();
            context.setVariable("logoPath", logourl);
            
            String htmlContent = templateEngine.process("pdf-template", context);
            
            // 폰트 등록
            String FONT_PATH = "C:/Windows/Fonts/D2Coding-Ver1.3.2-20180524-all.ttc";
            renderer.getFontResolver().addFont(FONT_PATH, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            
            String FONT_PATH2 = "C:/Windows/Fonts/batang.ttc";
            renderer.getFontResolver().addFont(FONT_PATH2, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            
            String FONT_PATH3 = "C:/Windows/Fonts/gulim.ttc";
            renderer.getFontResolver().addFont(FONT_PATH3, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

            renderer.setDocumentFromString(htmlContent, logourl);
            renderer.layout();
            renderer.createPDF(outputStream);
            
            // 임시 파일로 먼저 생성
//            File tempFile = File.createTempFile("certificate", ".pdf");
//            try (OutputStream tempOut = new FileOutputStream(tempFile)) {
//                renderer.createPDF(tempOut, false);
//                renderer.finishPDF();
//            }
//
//            // 3️⃣ 워터마크 추가
//            addWatermark(tempFile, outputStream);

//            tempFile.delete();

        } catch (Exception e) {
            throw new RuntimeException("PDF 생성 중 오류 발생: " + e.getMessage(), e);
        }
    }

    /**
     * PDF에 워터마크 이미지 추가
     */
//    private void addWatermark(File inputPdf, OutputStream outputStream) {
//        try {
//            PdfReader reader = new PdfReader(new FileInputStream(inputPdf));
//            PdfStamper stamper = new PdfStamper(reader, outputStream);
//
//            String imagePath = resourceLoader.getResource("classpath:images/new_logo.png").getFile().getAbsolutePath();
//            Image watermark = Image.getInstance(imagePath);
//
//            float opacity = 0.9f;
//            float scale = 3.0f;
//
//            int totalPages = reader.getNumberOfPages();
//            for (int i = 1; i <= totalPages; i++) {
//                PdfContentByte content = stamper.getUnderContent(i);
//                PdfGState gState = new PdfGState();
//                gState.setFillOpacity(opacity);
//                content.setGState(gState);
//
//                Rectangle pageSize = reader.getPageSizeWithRotation(i);
//                watermark.scalePercent(scale * 100);
//                float x = (pageSize.getWidth() - watermark.getScaledWidth()) / 2;
//                float y = (pageSize.getHeight() - watermark.getScaledHeight()) / 2;
//                watermark.setAbsolutePosition(x, y);
//                content.addImage(watermark);
//                
//            }
//
//            stamper.close();
//            reader.close();
//
//        } catch (Exception e) {
//            throw new RuntimeException("워터마크 추가 실패: " + e.getMessage(), e);
//        }
//    }
}
