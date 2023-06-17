package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    //2-
    public List<Student> getAllStudent() {
        //List<Student>students=studentRepository.findAll();
        //return students
        return studentRepository.findAll();
    }

    //4-
    public void saveStudent(Student student) {
        //student daha once kaydedilmis mi--> ayni emaile sahip student var mi?

        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new ConflictException("Email is already exists!");
        }
        studentRepository.save(student);
    }

    //6-
    public Student getStudentById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Student not found by id: " + id));
        return student;
    }

    //8-
    public void deleteStudet(Long id) {
        //bu id te sahip bir student var mi bilmiyoruz
        Student foundStudent = getStudentById(id);
        studentRepository.delete(foundStudent);
    }

    //10-
    public void updateStudent(Long id, StudentDTO studentDTO) {
        //gelen id ile ogrenci var mi? varsa getirelim
        Student foundStudent = getStudentById(id);

        //studentDTO.getEmail(); zaten daha onceden DB de varsa??
        boolean existEmail = studentRepository.existsByEmail(studentDTO.getEmail());

        //existsEmail true ise bu email baska bir studentin olabilir, student'in kendi emaili olabilir??
        //id:3 student email:a@email.com
        //dto               student
        //b@email.com       id:4 b@email.com->existsEmail:true-->exception
        //c@email.com       DB de yok-------->existsEmail:false-->update:OK
        //a@email.com       id:3 a@email.com ->existsEmail:true-->update:OK


        if (existEmail && !foundStudent.getEmail().equals(studentDTO.getEmail())) {
            throw new ConflictException("Email alredy exists!!");
        }

        foundStudent.setName(studentDTO.getName());
        foundStudent.setLastName(studentDTO.getLastName());
        foundStudent.setGrade(studentDTO.getGrade());
        foundStudent.setPhoneNumber(studentDTO.getPhoneNumber());

        studentRepository.save(foundStudent);//saveOrUpdate gibi islem yapar


    }

    public Page<Student> getAllStudentPaging(Pageable pageable) {

        return studentRepository.findAll(pageable);

    }

    //14-
    public List<Student> getAllStudentsByLastName(String lastName) {

        return studentRepository.findAllByLastName(lastName);//select * from student where lastName=''
    }

    public List<Student> getAllStudentByGrade(Integer grade) {

        //return studentRepository.findAllByGrade(grade);
        return studentRepository.findAllGradeEquals(grade);
    }

   // public StudentDTO getStudentDtoById(Long id) {
   //     Student student=getStudentById(id);
//
   //     //parametre olarak student objesinin kendisini verdigimizde DTO olusturan bir constructor
   //     StudentDTO studentDTO=new StudentDTO(student);
   //     return studentDTO;
//
   // }

    //studenti dto ya mapleme islemini JPQL ile yapsak nasil olur

    public StudentDTO getStudentDtoById(Long id) {
        StudentDTO studentDTO=studentRepository.findStudentDtoById(id).orElseThrow(()->
                new ResourceNotFoundException("Student not found by id: "+id));
        return studentDTO;

    }
}
