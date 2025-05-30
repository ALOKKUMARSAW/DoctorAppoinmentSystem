package com.medical.repository;

import com.medical.entity.Appointment;
import com.medical.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatient(User patient);
    List<Appointment> findByDoctorId(Long doctorId);
    List<Appointment> findByDoctorIdAndAppointmentDateTimeBetween(
        Long doctorId, LocalDateTime start, LocalDateTime end);
    List<Appointment> findByStatus(String status);
} 