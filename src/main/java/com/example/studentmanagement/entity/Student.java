package com.example.studentmanagement.entity;
import jakarta.persistence.*;
@Entity @Table(name="students") public class Student {
@Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
@Column(nullable=false) private String name;
@Column(nullable=false,unique=true) private String email;
private Integer age;
public Student(){} public Long getId(){return id;} public String getName(){return name;} public String getEmail(){return email;} public Integer getAge(){return age;}
public void setId(Long id){this.id=id;} public void setName(String name){this.name=name;} public void setEmail(String email){this.email=email;} public void setAge(Integer age){this.age=age;}
}