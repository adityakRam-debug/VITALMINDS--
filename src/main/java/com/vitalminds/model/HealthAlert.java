package com.vitalminds.model;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity @Table(name="health_alerts") public class HealthAlert {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id; @ManyToOne(fetch=FetchType.LAZY,optional=false) User user; LocalDateTime createdAt; String severity; String title; String message; boolean readFlag;
 public Long getId(){return id;} public User getUser(){return user;} public void setUser(User v){user=v;} public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;} public String getSeverity(){return severity;} public void setSeverity(String v){severity=v;} public String getTitle(){return title;} public void setTitle(String v){title=v;} public String getMessage(){return message;} public void setMessage(String v){message=v;} public boolean isReadFlag(){return readFlag;} public void setReadFlag(boolean v){readFlag=v;}
}
