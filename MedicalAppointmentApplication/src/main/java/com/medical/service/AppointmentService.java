package com.medical.service;

import com.medical.entity.Appointment;
import com.medical.entity.Doctor;
import com.medical.entity.User;
import com.medical.repository.AppointmentRepository;
import com.medical.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private UserService userService;
    
    public Appointment bookAppointment(Long patientId, Long doctorId, LocalDateTime dateTime, 
                                     String symptoms, String notes) {
        User patient = userService.findById(patientId);
        Doctor doctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new RuntimeException("Doctor not found"));
            
        // Check if the time slot is available
        List<Appointment> existingAppointments = appointmentRepository
            .findByDoctorIdAndAppointmentDateTimeBetween(
                doctorId, dateTime.minusMinutes(30), dateTime.plusMinutes(30));
                
        if (!existingAppointments.isEmpty()) {
            throw new RuntimeException("Time slot not available");
        }
        
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDateTime(dateTime);
        appointment.setStatus("SCHEDULED");
        appointment.setPaymentStatus("PENDING");
        appointment.setAmount(Double.parseDouble(doctor.getConsultationFee()));
        appointment.setSymptoms(symptoms);
        appointment.setNotes(notes);
        
        return appointmentRepository.save(appointment);
    }
    
    public Appointment cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
            .orElseThrow(() -> new RuntimeException("Appointment not found"));
            
        if (appointment.getStatus().equals("CANCELLED")) {
            throw new RuntimeException("Appointment already cancelled");
        }
        
        appointment.setStatus("CANCELLED");
        appointment.setPaymentStatus("REFUNDED");
        
        return appointmentRepository.save(appointment);
    }
    
    public List<Appointment> getPatientAppointments(Long patientId) {
        User patient = userService.findById(patientId);
        return appointmentRepository.findByPatient(patient);
    }
    
    public List<Appointment> getDoctorAppointments(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }
} 