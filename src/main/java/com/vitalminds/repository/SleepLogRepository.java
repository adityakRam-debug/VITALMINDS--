package com.vitalminds.repository; import com.vitalminds.model.SleepLog; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface SleepLogRepository extends JpaRepository<SleepLog,Long>{List<SleepLog> findTop7ByUserIdOrderByDateDesc(Long userId); List<SleepLog> findTop14ByUserIdOrderByDateDesc(Long userId);}
