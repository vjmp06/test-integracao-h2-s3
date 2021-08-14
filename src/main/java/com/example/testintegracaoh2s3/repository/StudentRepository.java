package com.example.testintegracaoh2s3.repository;

import com.example.testintegracaoh2s3.model.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository <Student, Long> {
    
}
