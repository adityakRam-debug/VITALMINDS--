package com.vitalminds.service;

import com.vitalminds.model.HealthRecord;
import com.vitalminds.model.User;
import com.vitalminds.repository.HealthRecordRepository;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.rendering.ImageType;
import org.springframework.beans.factory.annotation.Value;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class HealthReportService {
    private final HealthRecordRepository repository;
    private final Path uploadRoot; private final boolean ocrEnabled; private final String ocrCommand;

    public HealthReportService(HealthRecordRepository repository, @Value("${vitalminds.upload-dir:uploads}") String uploadDir, @Value("${vitalminds.ocr.enabled:true}") boolean ocrEnabled, @Value("${vitalminds.ocr.command:tesseract}") String ocrCommand) {
        this.repository = repository; this.uploadRoot = Paths.get(uploadDir).toAbsolutePath().normalize(); this.ocrEnabled=ocrEnabled; this.ocrCommand=ocrCommand;
    }

    public HealthRecord process(User user, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) throw new IllegalArgumentException("Please choose a PDF report.");
        if (file.getSize() > 10 * 1024 * 1024) throw new IllegalArgumentException("PDF must be 10 MB or smaller.");
        String type = file.getContentType();
        if (type == null || !type.equalsIgnoreCase("application/pdf")) throw new IllegalArgumentException("Only PDF files are allowed.");

        Files.createDirectories(uploadRoot);
        String safeName = System.currentTimeMillis() + "_" + sanitize(file.getOriginalFilename());
        Files.write(uploadRoot.resolve(safeName), file.getBytes(), StandardOpenOption.CREATE_NEW);

        String text;
        try (var doc = Loader.loadPDF(file.getBytes())) {
            text = new PDFTextStripper().getText(doc);
        }
        if (text == null || text.isBlank()) text = tryOcr(file.getBytes());

        HealthRecord r = new HealthRecord();
        r.setUser(user); r.setFileName(file.getOriginalFilename()); r.setUploadedAt(LocalDateTime.now()); r.setExtractedText(text);
        r.setHemoglobin(findDouble(text, "(?:hemoglobin|hb)\\s*[:=-]?\\s*(\\d+(?:\\.\\d+)?)\\s*(?:g/dl|g\\s*/\\s*dl)?"));
        r.setGlucose(findDouble(text, "(?:glucose|blood sugar|fasting glucose)\\s*[:=-]?\\s*(\\d+(?:\\.\\d+)?)\\s*(?:mg/dl)?"));
        r.setCholesterol(findDouble(text, "(?:total cholesterol|cholesterol)\\s*[:=-]?\\s*(\\d+(?:\\.\\d+)?)\\s*(?:mg/dl)?"));
        Matcher bp = Pattern.compile("(?i)(?:blood pressure|bp)\\s*[:=-]?\\s*(\\d{2,3})\\s*/\\s*(\\d{2,3})\\s*(?:mmhg)?").matcher(text);
        if (bp.find()) { r.setSystolic(Integer.valueOf(bp.group(1))); r.setDiastolic(Integer.valueOf(bp.group(2))); }
        r.setBmi(findDouble(text, "(?:bmi|body mass index)\\s*[:=-]?\\s*(\\d+(?:\\.\\d+)?)"));
        r.setSummary(buildSummary(r));
        return repository.save(r);
    }

    private String tryOcr(byte[] pdfBytes) {
        if(!ocrEnabled) return "";
        Path temp=null; try { temp=Files.createTempDirectory("vitalminds-ocr-"); Path pdf=temp.resolve("report.pdf"); Files.write(pdf,pdfBytes);
            try(var doc=Loader.loadPDF(pdfBytes)){ PDFRenderer renderer=new PDFRenderer(doc); StringBuilder out=new StringBuilder(); int pages=Math.min(doc.getNumberOfPages(),12); for(int i=0;i<pages;i++){ BufferedImage img=renderer.renderImageWithDPI(i,150,ImageType.RGB); Path png=temp.resolve("page-"+i+".png"); ImageIO.write(img,"png",png.toFile()); Process p=new ProcessBuilder(ocrCommand,png.toString(),"stdout","-l","eng").redirectErrorStream(true).start(); out.append(new String(p.getInputStream().readAllBytes(),java.nio.charset.StandardCharsets.UTF_8)).append("\n"); p.waitFor(); } return out.toString(); }
        } catch(Exception e){ return ""; } finally { if(temp!=null) try{Files.walk(temp).sorted(java.util.Comparator.reverseOrder()).forEach(x->{try{Files.deleteIfExists(x);}catch(Exception ignored){}});}catch(Exception ignored){} }
    }

    private String sanitize(String name) {
        if (name == null || name.isBlank()) return "health-report.pdf";
        return name.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
    private Double findDouble(String text, String regex) {
        Matcher m = Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(text);
        return m.find() ? Double.valueOf(m.group(1)) : null;
    }
    private String buildSummary(HealthRecord r) {
        StringBuilder s = new StringBuilder("Report processed. ");
        if (r.getHemoglobin()!=null) s.append("Hemoglobin ").append(r.getHemoglobin()).append(". ");
        if (r.getGlucose()!=null) s.append("Glucose ").append(r.getGlucose()).append(". ");
        if (r.getCholesterol()!=null) s.append("Cholesterol ").append(r.getCholesterol()).append(". ");
        if (r.getSystolic()!=null) s.append("BP ").append(r.getSystolic()).append('/').append(r.getDiastolic()).append(". ");
        if (r.getBmi()!=null) s.append("BMI ").append(r.getBmi()).append(". ");
        if (s.toString().equals("Report processed. ")) s.append("No supported values were detected; try a text-based PDF with clear test labels.");
        return s.toString();
    }
}
