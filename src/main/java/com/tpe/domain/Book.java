package com.tpe.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Book {//Many

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("bookName")//JSON formatında name fieldını bu key ile göster
    private String name;

    @JsonIgnore//book objemi JSON a maplerken student ı alma
    @ManyToOne//1-Student-->Many-Book
    private Student student;//One


}
