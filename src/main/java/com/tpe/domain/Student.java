package com.tpe.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor//tüm fieldlarla parametreli const
@NoArgsConstructor//default const
//@RequiredArgsConstructor//final veya notnull fieldlardan
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Getter
    //@Setter(AccessLevel.NONE)
    private Long id;

    @NotBlank(message = "name can not be space")
    @Size(min=2,max = 25,message = "name '${validatedValue}' must be between {min} and {max}")
    @Column(nullable = false,length = 25)
    /*final*/ private String name;

    @NotBlank(message = "lastname can not be space")
    @Size(min=2,max = 25,message = "lastname '${validatedValue}' must be between {min} and {max}")
    @Column(nullable = false,length = 25)
    /*final*/ private String lastName;


    /*final */private Integer grade;

    @Column(nullable = false,unique = true,length = 50)
    @Email(message = "Pleasei provide valid email")//aaa@bbb.ccc
    /*final*/ private String email;

    private String phoneNumber;
    private LocalDateTime createDate=LocalDateTime.now();

    @OneToMany(mappedBy = "student")
    private List<Book> books=new ArrayList<>();

    //getter-setter
    //parametreli/siz const


}
