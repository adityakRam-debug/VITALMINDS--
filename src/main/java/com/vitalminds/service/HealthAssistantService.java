package com.vitalminds.service;

import com.vitalminds.model.HealthRecord;
import com.vitalminds.model.User;
import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class HealthAssistantService {
    public String reply(User user, HealthRecord latest, String question) {
        String q = question == null ? "" : question.toLowerCase(Locale.ROOT).trim();
        if (q.isBlank()) return "Ask me about your latest report, BMI, glucose, blood pressure, cholesterol, goals or healthy daily habits.";
        if (q.contains("emergency") || q.contains("chest pain") || q.contains("breathing") || q.contains("unconscious"))
            return "If you have severe or rapidly worsening symptoms, seek urgent medical care or your local emergency service. VitalMinds cannot diagnose or handle emergencies.";
        if (latest == null)
            return "I don't have a health report yet. Upload a recent text-based PDF so I can explain the supported values stored by VitalMinds.";
        if (q.contains("bmi") || q.contains("weight"))
            return latest.getBmi() == null ? "Your latest report does not contain a readable BMI value." : "Your latest recorded BMI is " + latest.getBmi() + ". BMI is a screening measure, not a diagnosis, and should be interpreted with your overall health context.";
        if (q.contains("glucose") || q.contains("sugar"))
            return latest.getGlucose() == null ? "No readable glucose value was found in your latest report." : "Your latest recorded glucose value is " + latest.getGlucose() + " mg/dL. The meaning depends on whether the sample was fasting and on your clinician's reference range.";
        if (q.contains("blood pressure") || q.equals("bp") || q.contains("pressure"))
            return latest.getSystolic() == null ? "No readable blood-pressure value was found." : "Your latest recorded blood pressure is " + latest.getSystolic() + "/" + latest.getDiastolic() + " mmHg. Repeated readings should be interpreted by a qualified clinician.";
        if (q.contains("cholesterol"))
            return latest.getCholesterol() == null ? "No readable cholesterol value was found." : "Your latest recorded total cholesterol is " + latest.getCholesterol() + " mg/dL. A full lipid assessment may include additional values such as LDL and HDL.";
        if (q.contains("hemoglobin") || q.equals("hb"))
            return latest.getHemoglobin() == null ? "No readable hemoglobin value was found." : "Your latest recorded hemoglobin is " + latest.getHemoglobin() + " g/dL. Reference ranges vary by age, sex and laboratory.";
        if (q.contains("report") || q.contains("health"))
            return "Your latest report summary is: " + (latest.getSummary() == null ? "processed successfully" : latest.getSummary()) + ". I can explain individual values, but I cannot diagnose disease.";
        if (q.contains("exercise") || q.contains("workout")) return "For general wellness, build activity gradually, include strength and aerobic movement, and choose a routine you can maintain. Stop and seek medical advice for concerning symptoms.";
        if (q.contains("sleep")) return "A consistent sleep schedule, a relaxing wind-down routine and reducing late caffeine can support sleep quality. Persistent sleep problems deserve professional evaluation.";
        if (q.contains("water") || q.contains("hydration")) return "Hydration needs vary with body size, activity, climate and medical conditions. Drink regularly and follow any fluid limits given by your clinician.";
        return "I can help explain your latest recorded BMI, glucose, blood pressure, cholesterol or hemoglobin, and suggest general wellness habits. Try asking: 'What is my BMI?'";
    }
}
