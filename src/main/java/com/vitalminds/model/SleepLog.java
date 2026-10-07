package com.vitalminds.model;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="sleep_logs") public class SleepLog {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="user_id") private User user;
 private LocalDate date; private Double hours; private String quality;
 public Long getId(){return id;} public User getUser(){return user;} public void setUser(User v){user=v;} public LocalDate getDate(){return date;} public void setDate(LocalDate v){date=v;} public Double getHours(){return hours;} public void setHours(Double v){hours=v;} public String getQuality(){return quality;} public void setQuality(String v){quality=v;}
}
