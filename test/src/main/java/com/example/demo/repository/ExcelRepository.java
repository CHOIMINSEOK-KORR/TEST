package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.ExcelEntity;

@Repository
public interface ExcelRepository extends JpaRepository<ExcelEntity, Long> {
    
}
