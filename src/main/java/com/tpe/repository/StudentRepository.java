package com.tpe.repository;

import com.tpe.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository//opsiyonel
public interface StudentRepository extends JpaRepository<Student,Long> {
    Boolean existsByEmail(String email);//Long olmasi table daki id nin data tipinden dolayi

    List<Student> findAllByLastName(String lastName);



}
