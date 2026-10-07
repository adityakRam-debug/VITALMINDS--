package com.vitalminds.model;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="meal_logs") public class MealLog {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id; @ManyToOne(fetch=FetchType.LAZY,optional=false) User user; LocalDate date; String mealType; String food; Integer calories;
 public Long getId(){return id;} public User getUser(){return user;} public void setUser(User v){user=v;} public LocalDate getDate(){return date;} public void setDate(LocalDate v){date=v;} public String getMealType(){return mealType;} public void setMealType(String v){mealType=v;} public String getFood(){return food;} public void setFood(String v){food=v;} public Integer getCalories(){return calories;} public void setCalories(Integer v){calories=v;}
}
