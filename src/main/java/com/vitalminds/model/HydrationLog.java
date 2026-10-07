package com.vitalminds.model;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="hydration_logs") public class HydrationLog {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id; @ManyToOne(fetch=FetchType.LAZY,optional=false) User user; LocalDate date; Double liters; Integer glasses;
 public Long getId(){return id;} public User getUser(){return user;} public void setUser(User v){user=v;} public LocalDate getDate(){return date;} public void setDate(LocalDate v){date=v;} public Double getLiters(){return liters;} public void setLiters(Double v){liters=v;} public Integer getGlasses(){return glasses;} public void setGlasses(Integer v){glasses=v;}
}
