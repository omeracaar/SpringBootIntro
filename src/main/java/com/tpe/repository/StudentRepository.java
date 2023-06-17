package com.tpe.repository;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository//opsiyonel
public interface StudentRepository extends JpaRepository<Student,Long> {
    Boolean existsByEmail(String email);//Long olmasi table daki id nin data tipinden dolayi

    List<Student> findAllByLastName(String lastName);


    List<Student> findAllByGrade(Integer grade);

    //JPQL
    @Query("SELECT s FROM Student s WHERE s.grade=:pGrade")
    List<Student> findAllGradeEquals(@Param("pGrade") Integer grade);

    //SQL
   // @Query(value = "SELECT * FROM student s WHERE s.grade=:pGrade",nativeQuery = true)//Student
   // List<Student> findAllGradeEquals(@Param("pGrade") Integer grade);

    //DB den gelen studenti DTO ya cevirerek gonderiyor
    @Query("SELECT new StudentDTO(s) FROM Student s WHERE s.id=:pID")
    Optional<StudentDTO> findStudentDtoById(@Param("pId") Long id);


}
