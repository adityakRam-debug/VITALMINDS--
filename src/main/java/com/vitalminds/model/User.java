package com.vitalminds.model;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String name;
 @Column(nullable=false, unique=true) private String email;
 @Column(nullable=false) private String password;
 private Integer age;
 private String gender;
 private Double heightCm;
 private Double weightKg;
 private String goal;
 private String role="USER";
 public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
 public String getEmail(){return email;} public void setEmail(String v){email=v;} public String getPassword(){return password;} public void setPassword(String v){password=v;}
 public Integer getAge(){return age;} public void setAge(Integer v){age=v;} public String getGender(){return gender;} public void setGender(String v){gender=v;}
 public Double getHeightCm(){return heightCm;} public void setHeightCm(Double v){heightCm=v;} public Double getWeightKg(){return weightKg;} public void setWeightKg(Double v){weightKg=v;}
 public String getGoal(){return goal;} public void setGoal(String v){goal=v;} public String getRole(){return role;} public void setRole(String v){role=v;}
}
