package com.vitalminds.repository; import com.vitalminds.model.Appointment; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface AppointmentRepository extends JpaRepository<Appointment,Long>{List<Appointment> findTop6ByUserIdOrderByAppointmentAtAsc(Long userId);}
