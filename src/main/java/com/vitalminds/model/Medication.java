package com.vitalminds.model;
import jakarta.persistence.*;
@Entity @Table(name="medications") public class Medication {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="user_id") private User user;
 private String name; private String dosage; private String frequency; private String notes; private boolean active=true;
 public Long getId(){return id;} public User getUser(){return user;} public void setUser(User v){user=v;} public String getName(){return name;} public void setName(String v){name=v;} public String getDosage(){return dosage;} public void setDosage(String v){dosage=v;} public String getFrequency(){return frequency;} public void setFrequency(String v){frequency=v;} public String getNotes(){return notes;} public void setNotes(String v){notes=v;} public boolean isActive(){return active;} public void setActive(boolean v){active=v;}
}
