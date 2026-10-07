package com.vitalminds.model;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="workout_plans") public class WorkoutPlan {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id; @ManyToOne(fetch=FetchType.LAZY,optional=false) User user; LocalDate date; String type; Integer minutes; String intensity; String notes;
 public Long getId(){return id;} public User getUser(){return user;} public void setUser(User v){user=v;} public LocalDate getDate(){return date;} public void setDate(LocalDate v){date=v;} public String getType(){return type;} public void setType(String v){type=v;} public Integer getMinutes(){return minutes;} public void setMinutes(Integer v){minutes=v;} public String getIntensity(){return intensity;} public void setIntensity(String v){intensity=v;} public String getNotes(){return notes;} public void setNotes(String v){notes=v;}
}
