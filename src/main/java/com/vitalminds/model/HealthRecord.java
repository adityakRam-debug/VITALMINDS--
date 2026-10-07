package com.vitalminds.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "health_records")
public class HealthRecord {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;
    private String fileName;
    private LocalDateTime uploadedAt;
    @Column(columnDefinition = "TEXT") private String extractedText;
    private Double hemoglobin;
    private Double glucose;
    private Double cholesterol;
    private Integer systolic;
    private Integer diastolic;
    private Double bmi;
    @Column(length = 1200) private String summary;

    public Long getId(){return id;} public void setId(Long v){id=v;}
    public User getUser(){return user;} public void setUser(User v){user=v;}
    public String getFileName(){return fileName;} public void setFileName(String v){fileName=v;}
    public LocalDateTime getUploadedAt(){return uploadedAt;} public void setUploadedAt(LocalDateTime v){uploadedAt=v;}
    public String getExtractedText(){return extractedText;} public void setExtractedText(String v){extractedText=v;}
    public Double getHemoglobin(){return hemoglobin;} public void setHemoglobin(Double v){hemoglobin=v;}
    public Double getGlucose(){return glucose;} public void setGlucose(Double v){glucose=v;}
    public Double getCholesterol(){return cholesterol;} public void setCholesterol(Double v){cholesterol=v;}
    public Integer getSystolic(){return systolic;} public void setSystolic(Integer v){systolic=v;}
    public Integer getDiastolic(){return diastolic;} public void setDiastolic(Integer v){diastolic=v;}
    public Double getBmi(){return bmi;} public void setBmi(Double v){bmi=v;}
    public String getSummary(){return summary;} public void setSummary(String v){summary=v;}
}
