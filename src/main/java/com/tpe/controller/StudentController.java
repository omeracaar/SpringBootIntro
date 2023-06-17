package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Controller
@RestController
@RequestMapping("/students")//http:/localhost:8080/students
public class StudentController {

    @Autowired
    private StudentService studentService;

    //spring boot u selamlama:)
    //https:/localhost:8080/students/greet
    @GetMapping("/greet")
    public String greet(){
        return "Hello Spring Boot";
    }

    //1-Tum studentleri listeleyelim
    //https:/localhost:8080/students + GET
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents(){
        List<Student> studentList=studentService.getAllStudent();
        //return new ResponseEntity<>(studentList, HttpStatus.OK);//200
        return ResponseEntity.ok(studentList);//200
    }
    //response:body(data)+HTTP status code
    //ResponseEntity: response bodysi ile birlikte HTTP status code nu göndermemizi sağlar.
    //ResponseEntity.ok methodu HTTP status olarak OK ya da 200 donmek icin bir kisayoldur.

    //2-yeni bir student CREATE etme
    //https:/localhost:8080/students + POST + RequestBody(JSON)
    @PostMapping
    public ResponseEntity<Map<String,String>> createStudent(@Valid @RequestBody Student student){

        studentService.saveStudent(student);

        Map<String,String> response=new HashMap<>();
        response.put("message","Student is created succesfully");
        response.put("status","succes");
        return new ResponseEntity<>(response,HttpStatus.CREATED);//201
    }
//@RequestBody:HTTP request body'sindeki json formatindaki datayi student objesine mapler.
// (Entity obje<->JSON)--> Jackson Kutushanesi yapar

    //5-Belirli bir id ile studenti goruntuleme + RequestParam
    //https:/localhost:8080/students/query?id=1 + GET
    @GetMapping("/query")
    public ResponseEntity<Student> getStudent(@RequestParam("id") Long id){

        Student student=studentService.getStudentById(id);

        return new ResponseEntity<>(student,HttpStatus.OK);//200
    }

    //5'-Belirli bir id ile studenti goruntuleme + PathParam
    //https:/localhost:8080/students/query?id=1 + GET
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id){

        Student student=studentService.getStudentById(id);

        return new ResponseEntity<>(student,HttpStatus.OK);//200
    }

    //clienttan bilgi almak icin:1-JSON formatinda request body olarak
    //                           2-request param query?id=1
    //                           3-path param /1

    //7-Belirli bir id ile studenti silelim
    //https:/localhost:8080/students/query?id=1 + GET
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String>> deleteStudent(@PathVariable Long id){

        studentService.deleteStudet(id);

        Map<String,String> response=new HashMap<>();
        response.put("message","Student is deleted succesfully..");
        response.put("status","succes");


        return ResponseEntity.ok(response);//200
    }

    //9-Belirli bir id ile studenti update edelim.(name,lastName,grade,email,phoneNumber)
    //https:/localhost:8080/students/1 + UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Map<String,String>> updateStudent(@PathVariable ("id") Long id,
                                                            @Valid @RequestBody StudentDTO studentDTO){

        studentService.updateStudent(id,studentDTO);

        Map<String,String> response=new HashMap<>();
        response.put("message","Student  is updated succesfully");
        response.put("status","succes");
        return ResponseEntity.ok(response);//200

    }

    //11-pagination-sayfalandirma
    //tum kayitlari page page listeleyelim
    //https:/localhost:8080/students/page?
    // page=1&
    // size=10&
    // sort=name&
    // direction=DESC + GET
    @GetMapping("/page")
    public ResponseEntity<Page<Student>> getAllStudentByPage
    (@RequestParam(value = "page",required = false,defaultValue = "0") int page,//hangi page gösterilsin ilk deger 0
     @RequestParam("size") int size,//kaç kayıt
     @RequestParam("sort") String prop,//hangi fielda göre
     @RequestParam("direction") Sort.Direction direction)//sıralama yönü
    {
        Pageable pageable= PageRequest.of(page,size,Sort.by(direction,prop));
        Page<Student> studentsByPage=studentService.getAllStudentPaging(pageable);
        return new ResponseEntity<>(studentsByPage,HttpStatus.OK);
    }

    //page,size,sort,direction parametrelerinin girilmesini opsiyonel yapabiliriz.(required=false)
    //parametrelerin girilmesi opsiyonel oldugunda default value vermeliyiz.

    //13-lastName ile studentlari listeleyelim.
    //https:/localhost:8080/students/querylastname?lastName=Bey
    @GetMapping("/{lastName}")
    public ResponseEntity<List<Student>> getAllStudentsByLastName(@RequestParam("lastName") String lastName){
        List<Student> studentList = studentService.getAllStudentsByLastName(lastName);
        return ResponseEntity.ok(studentList);
    }

    //15-grade ile studentları listeleyelim. **ÖDEV**
    ////http://localhost:8080/students/grade/99

    @GetMapping("/grade{grade}")
    public ResponseEntity<List<Student>> getAllStudentByGrade(@PathVariable("grade") Integer grade){

        List<Student> studentList=studentService.getAllStudentByGrade(grade);
        return ResponseEntity.ok(studentList);
    }

    //17-idsi verilen studentin goruntuleme requestine response olarak DTO donelim.
    ////http://localhost:8080/students//dto/1 + GET
    @GetMapping("/dto/{id}")
    public ResponseEntity<StudentDTO> getStudentDtoById(Long id){
        StudentDTO studentDTO=studentService.getStudentDtoById(id);
        return ResponseEntity.ok(studentDTO);
    }





}
