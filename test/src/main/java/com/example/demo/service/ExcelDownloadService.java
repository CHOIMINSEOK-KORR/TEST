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

import com.example.demo.Entity.ExcelEntity;
import com.example.demo.repository.ExcelRepository;

@Service
public class ExcelDownloadService {

	private final ExcelRepository excelRepository;

	public ExcelDownloadService(ExcelRepository excelRepository) {
		this.excelRepository = excelRepository;
	}
	// =====================


	public ByteArrayInputStream exportExcel() throws IOException{
		List<ExcelEntity> ExcelDataList = excelRepository.findAll();
		
		Workbook workBook = new XSSFWorkbook();
		Sheet sheet = workBook.createSheet("테스트");

		// 헤더 폰트 스타일
		Font headerFont = workBook.createFont();
		headerFont.setBold(true);
		headerFont.setColor(IndexedColors.BLACK.getIndex());
		
		// 헤더 셀 스타일
		CellStyle headerStyle = workBook.createCellStyle();
		headerStyle.setFont(headerFont);
		
		// 헤더 배경 색 지정
		headerStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		
		// 셀 배경을 단색으로 채우기
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        // 헤더 중앙정렬
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
		
        // ==========================
        
        // 데이터 셀 스타일
        CellStyle cellStyle = workBook.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setVerticalAlignment(VerticalAlignment.TOP); // 위쪽 정렬
        
        
        // 데이터 초과
        CellStyle wrapCellStyle = workBook.createCellStyle();
        wrapCellStyle.setBorderBottom(BorderStyle.THIN);
        wrapCellStyle.setBorderTop(BorderStyle.THIN);
        wrapCellStyle.setBorderLeft(BorderStyle.THIN);
        wrapCellStyle.setBorderRight(BorderStyle.THIN);
        wrapCellStyle.setWrapText(true); // 50자 이상이면 다음줄로 넘어가게 하는 메서드
        wrapCellStyle.setVerticalAlignment(VerticalAlignment.TOP);
		
        // ==========================
        
        // 헤더 작성
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

            Cell cell0 = row.createCell(colIdx++); // 0열 1행
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

            Cell cell7 = row.createCell(colIdx++); // 7열
            cell7.setCellValue(data.getCompletionStatus());
            cell7.setCellStyle(cellStyle);
        }
        
        // 컬럼 너비 자동 조정
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
            
            // 현재 자동조정된 너비
            int currentWidth = sheet.getColumnWidth(i);
            
            int extraPadding = 1024;
            
            if(i == 1 || i == 3 || i == 5) {
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
