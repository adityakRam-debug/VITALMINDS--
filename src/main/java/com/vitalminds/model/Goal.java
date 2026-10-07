package com.vitalminds.model;
import jakarta.persistence.*; import java.time.LocalDate; import java.time.LocalDateTime;
@Entity @Table(name="goals") public class Goal {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="user_id") private User user;
 private String title; private String category; private Double target; private Double progress; private String unit; private LocalDate targetDate; private LocalDateTime createdAt;
 public Long getId(){return id;} public User getUser(){return user;} public void setUser(User v){user=v;} public String getTitle(){return title;} public void setTitle(String v){title=v;} public String getCategory(){return category;} public void setCategory(String v){category=v;} public Double getTarget(){return target;} public void setTarget(Double v){target=v;} public Double getProgress(){return progress;} public void setProgress(Double v){progress=v;} public String getUnit(){return unit;} public void setUnit(String v){unit=v;} public LocalDate getTargetDate(){return targetDate;} public void setTargetDate(LocalDate v){targetDate=v;} public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}
