package com.vitalminds.model;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity @Table(name="appointments") public class Appointment {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="user_id") private User user;
 private String doctorName; private String specialty; private LocalDateTime appointmentAt; private String notes;
 public Long getId(){return id;} public User getUser(){return user;} public void setUser(User v){user=v;} public String getDoctorName(){return doctorName;} public void setDoctorName(String v){doctorName=v;} public String getSpecialty(){return specialty;} public void setSpecialty(String v){specialty=v;} public LocalDateTime getAppointmentAt(){return appointmentAt;} public void setAppointmentAt(LocalDateTime v){appointmentAt=v;} public String getNotes(){return notes;} public void setNotes(String v){notes=v;}
}
