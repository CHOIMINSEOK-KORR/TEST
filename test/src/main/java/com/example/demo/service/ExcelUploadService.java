package com.example.demo.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Entity.ExcelEntity;
import com.example.demo.repository.ExcelRepository;

@Service
public class ExcelUploadService {

	private final ExcelRepository enrollmentRepository;

    public ExcelUploadService(ExcelRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }
    
    // ===========================

	public List<ExcelEntity> readExcelFile(MultipartFile file) throws IOException {
		
		List<ExcelEntity> enrollments = new ArrayList<>();
		
		try (InputStream is = file.getInputStream();
	             Workbook workbook = new XSSFWorkbook(is)) {

	            Sheet sheet = workbook.getSheetAt(0);
	            
	            // 첫 번째 행은 헤더이므로 건너뜁니다 (rowNum = 0은 헤더).
	            // 데이터는 두 번째 행 (rowNum = 1)부터 시작합니다.
	            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
	                Row row = sheet.getRow(i);
	                if (row == null) continue;

	                ExcelEntity entity = new ExcelEntity();
	                
	                // 주의: Cell 순서(0, 1, 2, 3)는 엑셀 파일의 열 순서와 일치해야 합니다.
	                // 엑셀에는 ID(번호)가 없다고 가정하고 ID는 JPA가 자동으로 생성하게 둡니다.
	                
	                // 엑셀 셀 값을 읽고 Entity의 Setter를 사용하여 값 주입
	                // 1. 성명 (Column 1)
	                Cell cell1 = row.getCell(1);
	                entity.setStudentName(getStringValue(cell1));
	                
	                // 2. 강좌명 (Column 2)
	                Cell cell2 = row.getCell(2);
//	                entity.setCourseTitle(getStringValue(cell2));
	                
	                // 3. 수료일 (Column 3)
	                Cell cell3 = row.getCell(3);
//	                entity.setCompletionDate(getStringValue(cell3));
	                
	                enrollments.add(entity);
	            }

	        } catch (Exception e) {
	            // 파일 읽기 중 오류 발생 시
	            throw new RuntimeException("엑셀 파일 처리 중 오류가 발생했습니다: " + e.getMessage());
	        }
		
		return enrollments;
	}
	
	// 엑셀에서 읽은 데이터 db저장
	public List<ExcelEntity> saveEnrollments(List<ExcelEntity> enrollments) {
		return enrollmentRepository.saveAll(enrollments);
	}
	

	// 셀타입을 확인하고 문자열 값으로 변환하는 유틸리티 메서드
	private String getStringValue(Cell cell) {
		if (cell == null) {
            return "";
		}
		
         // 셀 타입에 따라 값을 읽습니다.
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue();
                case NUMERIC:
                    // 날짜 형식일 경우 (수료일)
                    if (DateUtil.isCellDateFormatted(cell)) {
                        // 날짜 형식을 원하는 문자열 포맷으로 변환해야 함 (여기서는 임시로 toString 사용)
                        return cell.getDateCellValue().toString();
                    }
                    // 숫자일 경우
                    return String.valueOf((int) cell.getNumericCellValue());
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    return cell.getCellFormula();
                default:
                    return "";
            }
        }
	}

    
    
