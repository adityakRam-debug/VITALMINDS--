package com.vitalminds.repository; import com.vitalminds.model.ActivityLog; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface ActivityLogRepository extends JpaRepository<ActivityLog,Long>{List<ActivityLog> findTop7ByUserIdOrderByDateDesc(Long userId); List<ActivityLog> findTop14ByUserIdOrderByDateDesc(Long userId);}
