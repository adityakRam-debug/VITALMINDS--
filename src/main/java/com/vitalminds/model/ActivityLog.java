package com.vitalminds.model;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="activity_logs") public class ActivityLog {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="user_id") private User user;
 private LocalDate date; private Integer steps; private Integer minutes; private String activityType;
 public Long getId(){return id;} public User getUser(){return user;} public void setUser(User v){user=v;} public LocalDate getDate(){return date;} public void setDate(LocalDate v){date=v;} public Integer getSteps(){return steps;} public void setSteps(Integer v){steps=v;} public Integer getMinutes(){return minutes;} public void setMinutes(Integer v){minutes=v;} public String getActivityType(){return activityType;} public void setActivityType(String v){activityType=v;}
}
