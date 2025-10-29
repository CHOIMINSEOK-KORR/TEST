package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Entity.ExcelEntity;
import com.example.demo.service.ExcelDownloadService;
import com.example.demo.service.ExcelUploadService;

import jakarta.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/test")
public class ExcelController {

	private final ExcelDownloadService excelDownloadService;
	private final ExcelUploadService excelUploadService;

	public ExcelController(ExcelDownloadService excelDownloadService, ExcelUploadService excelUploadService) {
		this.excelDownloadService = excelDownloadService;
		this.excelUploadService = excelUploadService;
	}
	
	// ==============================
	
	// 엑셀 다운로드
    @GetMapping("/excel/download")
    public ResponseEntity<InputStreamResource> downloadExcel() throws IOException {
        ByteArrayInputStream inputStream = excelDownloadService.exportExcel();

        HttpHeaders headers = new HttpHeaders();
        // 엑셀 다운로드 형식
        headers.add("Content-Disposition", "attachment; filename=excel_data.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(inputStream));
    }
	
    
    
    // 엑셀 업로드 페이지
	@GetMapping("/upload-page")
	public String uploadPage() {
		return "uploadForm";
	}
	
	
	// 엑셀 업로드
	@PostMapping("/upload/excel")
	public String uploadExcel(@RequestParam("file") MultipartFile file) {
		if(file.isEmpty()) { // 파일이 없을 경우
			return "redirect:/upload-page?error=file_empty";
		}
		try {
            // 1. 엑셀 파일 읽기 (List<Enrollment> 반환)
            List<ExcelEntity> enrollments = excelUploadService.readExcelFile(file);

            // 2. DB에 데이터 저장
            List<ExcelEntity> savedEntities = excelUploadService.saveEnrollments(enrollments);
            
            // 성공 메시지 반환 또는 리다이렉트
            // 실제 사용 환경에서는 적절한 View나 JSON 응답이 필요합니다.
            return "redirect:/test/upload-success?count=" + savedEntities.size(); 
            
        } catch (Exception e) {
            // 오류 발생 시 처리
            e.printStackTrace();
            return "redirect:/test/upload-page?error=processing_failed";
        }
	}
	
	
	@GetMapping("/upload-success")
	public String uploadSuccess(@RequestParam("count") int count, Model model) {
	    model.addAttribute("count", count);
	    return "uploadSuccessForm";
	}
	
}
