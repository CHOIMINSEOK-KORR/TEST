package com.example.demo.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.EducationEntity;
import com.example.demo.Entity.ExcelEntity;
import com.example.demo.repository.EducationRepository;
import com.example.demo.repository.ExcelRepository;

import lombok.RequiredArgsConstructor;

@Service
public class ExcelDownloadService {

	private final ExcelRepository excelRepository;
	private final EducationRepository educationRepository;

	public ExcelDownloadService(ExcelRepository excelRepository, EducationRepository educationRepository) {
		this.excelRepository = excelRepository;
        this.educationRepository = educationRepository;
	}

	// =====================


	public ByteArrayInputStream exportExcel() throws IOException{
		List<ExcelEntity> ExcelDataList = excelRepository.findAll();
        List<EducationEntity> EducationDataList = educationRepository.findAll();

		
		Workbook workBook = new XSSFWorkbook();
		Sheet sheet = workBook.createSheet("테스트");

		// 헤더 폰트 스타일(ExcelEntity)
		Font headerFont = workBook.createFont();
		headerFont.setBold(true);
		headerFont.setColor(IndexedColors.BLACK.getIndex());
        
		// 헤더 폰트 스타일(EducationEntity)
		Font headerFont2 = workBook.createFont();
		headerFont2.setBold(true);
		headerFont2.setColor(IndexedColors.BLACK.getIndex());
        // =======================================================
		
		// 헤더 셀 스타일(ExcelEntity)
		CellStyle headerStyle = workBook.createCellStyle();
		headerStyle.setFont(headerFont);
        
		// 헤더 셀 스타일(EducationEntity)
		CellStyle headerStyle2 = workBook.createCellStyle();
		headerStyle2.setFont(headerFont2);
        // =======================================================
		
		// 헤더 배경 색 지정(ExcelEntity)
		headerStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        
		// 헤더 배경 색 지정(EducationEntity)
		headerStyle2.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
        // =======================================================
		
		// 셀 배경을 단색으로 채우기
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        // 헤더 중앙정렬(ExcelEntity)
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);

        // 헤더 중앙정렬(EducationEntity)
        headerStyle2.setAlignment(HorizontalAlignment.CENTER);
        headerStyle2.setBorderBottom(BorderStyle.THIN);
        headerStyle2.setBorderTop(BorderStyle.THIN);
        headerStyle2.setBorderLeft(BorderStyle.THIN);
        headerStyle2.setBorderRight(BorderStyle.THIN);
		
        // ========================================================
        
        // 데이터 셀 스타일(ExcelEntity)
        CellStyle cellStyle = workBook.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setVerticalAlignment(VerticalAlignment.TOP); // 위쪽 정렬

        // 데이터 셀 스타일(EducationEntity)
        CellStyle cellStyle2 = workBook.createCellStyle();
        cellStyle2.setBorderBottom(BorderStyle.THIN);
        cellStyle2.setBorderTop(BorderStyle.THIN);
        cellStyle2.setBorderLeft(BorderStyle.THIN);
        cellStyle2.setBorderRight(BorderStyle.THIN);
        cellStyle2.setVerticalAlignment(VerticalAlignment.TOP); // 위쪽 정렬
        
        // 데이터 초과(ExcelEntity)
        CellStyle wrapCellStyle = workBook.createCellStyle();
        wrapCellStyle.setBorderBottom(BorderStyle.THIN);
        wrapCellStyle.setBorderTop(BorderStyle.THIN);
        wrapCellStyle.setBorderLeft(BorderStyle.THIN);
        wrapCellStyle.setBorderRight(BorderStyle.THIN);
        wrapCellStyle.setWrapText(true); // 50자 이상이면 다음줄로 넘어가게 하는 메서드
        wrapCellStyle.setVerticalAlignment(VerticalAlignment.TOP);

        // 데이터 초과(EducationEntity)
        CellStyle wrapCellStyle2 = workBook.createCellStyle();
        wrapCellStyle2.setBorderBottom(BorderStyle.THIN);
        wrapCellStyle2.setBorderTop(BorderStyle.THIN);
        wrapCellStyle2.setBorderLeft(BorderStyle.THIN);
        wrapCellStyle2.setBorderRight(BorderStyle.THIN);
        wrapCellStyle2.setWrapText(true); // 50자 이상이면 다음줄로 넘어가게 하는 메서드
        wrapCellStyle2.setVerticalAlignment(VerticalAlignment.TOP);
		
        // ==========================
        
        // 헤더 작성(ExcelEntity)
        Row headerRow = sheet.createRow(0);
        String[] headers = {"번호", "이름", "생년월일", "진행률", "중간평가", "최종시험", "총점", "수료여부"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // 데이터 작성
        int rowIdx = 1;
        final int wrapLimit = 50;
        
        for (ExcelEntity data : ExcelDataList) {
            Row row = sheet.createRow(rowIdx++);
            int colIdx = 0;

            Cell cell0 = row.createCell(colIdx++); // 0열
            cell0.setCellValue(data.getId());
            cell0.setCellStyle(cellStyle);

            Cell cell1 = row.createCell(colIdx++);
            cell1.setCellValue(data.getStudentName());
            cell1.setCellStyle(cellStyle);

            Cell cell2 = row.createCell(colIdx++);
            cell2.setCellValue(data.getBirthday());
            cell2.setCellStyle(cellStyle);

            String progressRate = data.getProgressRate();
            Cell cell3 = row.createCell(colIdx++);
            cell3.setCellValue(progressRate);
            
            if(progressRate != null && progressRate.length() > wrapLimit) {
            	cell3.setCellStyle(wrapCellStyle);
            } else {
            	cell3.setCellStyle(wrapCellStyle);
            }

            Cell cell4 = row.createCell(colIdx++);
            cell4.setCellValue(data.getMidtermEvaluation());
            cell4.setCellStyle(cellStyle);

            Cell cell5 = row.createCell(colIdx++);
            cell5.setCellValue(data.getFinalExam());
            cell5.setCellStyle(cellStyle);

            Cell cell6 = row.createCell(colIdx++);
            cell6.setCellValue(data.getTotalScore());
            cell6.setCellStyle(cellStyle);

            String completionStatus = data.getCompletionStatus();
            Cell cell7 = row.createCell(colIdx++);
            cell7.setCellValue(completionStatus);
            
            if(completionStatus != null && completionStatus.length() > wrapLimit) {
            	cell7.setCellStyle(wrapCellStyle);
            } else {
            	cell7.setCellStyle(wrapCellStyle);
            }
        }
        
        // 컬럼 너비 자동 조정
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
            
            // 현재 자동조정된 너비
            int currentWidth = sheet.getColumnWidth(i);
            
            int extraPadding = 1024;
            
            if(i == 1 || i == 3 || i == 5 | i==7) {
            	extraPadding = 3072;
            }
            
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) + extraPadding);
        }

        // 헤더 작성(EducationEntity)
        Row headerRow2 = sheet.createRow(3332);
        String[] headers2 = {"번호", "차수", "훈련제목", "훈련내용"};
        for (int i = 0; i < headers2.length; i++) {
            Cell cell = headerRow2.createCell(i);
            cell.setCellValue(headers2[i]);
            cell.setCellStyle(headerStyle2);
        }

        // 데이터 작성(EducationEntity)
        int rowIdx2 = 3333;
        // final int wrapLimit = 50;
        
        for (EducationEntity data : EducationDataList) {
            Row row = sheet.createRow(rowIdx2++);
            int colIdx = 0;

            Cell cell0 = row.createCell(colIdx++); // 0열 1행
            cell0.setCellValue(data.getId());
            cell0.setCellStyle(cellStyle2);

            Cell cell1 = row.createCell(colIdx++);
            cell1.setCellValue(data.getSession());
            cell1.setCellStyle(cellStyle2);

            Cell cell2 = row.createCell(colIdx++);
            cell2.setCellValue(data.getTrainingTitle());
            cell2.setCellStyle(cellStyle2);

             String trainingContent = data.getTrainingContent();
            Cell cell3 = row.createCell(colIdx++);
            cell3.setCellValue(trainingContent);
            
            if(trainingContent != null && trainingContent.length() > wrapLimit) {
            	cell3.setCellStyle(wrapCellStyle2);
            } else {
            	cell3.setCellStyle(wrapCellStyle2);
            }

        }

        // 컬럼 너비 자동 조정
        for (int i = 0; i < headers2.length; i++) {
            sheet.autoSizeColumn(i);
            
            // 현재 자동조정된 너비
            int currentWidth = sheet.getColumnWidth(i);
            
            int extraPadding = 1024;
            
            if(i == 3) {
            	extraPadding = 3072;
            }
            
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) + extraPadding);
        }
        
        // ======================================
        
        // 이미지 파일을 바이트 배열로 로드
        byte[] imageBytes;
        try {
        	ClassPathResource resource = new ClassPathResource("images/bonobono.jpg");
        	imageBytes = resource.getInputStream().readAllBytes();
        	
        } catch (IOException e) {
        	System.err.println("이미지 파일을 읽어오는 중 오류 발생: " + e.getMessage());
        	imageBytes = null;
		}
        
        if (imageBytes != null) {
        	
            // 워크북에 이미지를 등록
            int pictureIdx = workBook.addPicture(imageBytes, Workbook.PICTURE_TYPE_JPEG); 

            // 그림을 그릴 헬퍼 및 드로잉 객체 생성
            CreationHelper helper = workBook.getCreationHelper();
            Drawing drawing = sheet.createDrawingPatriarch();

            // 이미지 위치 및 크기 설정
            // ClientAnchor는 이미지가 시작하고 끝나는 셀과 셀 내의 위치를 지정
            ClientAnchor anchor = helper.createClientAnchor();
            
            anchor.setCol1(10); // 시작 컬럼
            anchor.setRow1(5);  // 시작 행
            anchor.setCol2(25); // 끝 컬럼
            anchor.setRow2(27); // 끝 행
            
            // 앵커를 적용
            drawing.createPicture(anchor, pictureIdx);
            
        }
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workBook.write(outputStream);
        workBook.close(); // workBook 객체 사용하고나면 메모리 누수 등 자원을 끊어야함
        
        return new ByteArrayInputStream(outputStream.toByteArray());
	}


	
	
	
}
