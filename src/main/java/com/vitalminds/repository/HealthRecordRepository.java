package com.vitalminds.repository;
import com.vitalminds.model.HealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface HealthRecordRepository extends JpaRepository<HealthRecord,Long>{
    List<HealthRecord> findTop20ByUserIdOrderByUploadedAtDesc(Long userId);
    List<HealthRecord> findTop20ByUserIdOrderByUploadedAtAsc(Long userId);
}
