package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.testDTO;

@Mapper
public interface EnrollmentMapper {

	// 엑셀 데이터화
	List<testDTO> getData();

}
