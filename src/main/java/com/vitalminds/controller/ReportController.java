package com.vitalminds.controller;

import com.vitalminds.service.HealthReportService;
import com.vitalminds.service.UserService;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ReportController {
    private final HealthReportService reports; private final UserService users;
    public ReportController(HealthReportService reports,UserService users){this.reports=reports;this.users=users;}
    @PostMapping("/api/reports/upload")
    public String upload(Authentication auth,@RequestParam("file") MultipartFile file){
        try{reports.process(users.findByEmail(auth.getName()).orElseThrow(),file);return "redirect:/dashboard?reportUploaded";}
        catch(Exception e){return "redirect:/dashboard?error="+url(e.getMessage());}
    }
    private String url(String s){return java.net.URLEncoder.encode(s==null?"Could not process report":s,java.nio.charset.StandardCharsets.UTF_8);}
}
