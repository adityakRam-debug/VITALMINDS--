package com.vitalminds.model;
import jakarta.persistence.*; import java.time.LocalTime;
@Entity @Table(name="reminders") public class Reminder {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="user_id") private User user;
 private String title; private String type; private LocalTime reminderTime; private boolean active=true; private String notes;
 public Long getId(){return id;} public User getUser(){return user;} public void setUser(User v){user=v;} public String getTitle(){return title;} public void setTitle(String v){title=v;} public String getType(){return type;} public void setType(String v){type=v;} public LocalTime getReminderTime(){return reminderTime;} public void setReminderTime(LocalTime v){reminderTime=v;} public boolean isActive(){return active;} public void setActive(boolean v){active=v;} public String getNotes(){return notes;} public void setNotes(String v){notes=v;}
}
