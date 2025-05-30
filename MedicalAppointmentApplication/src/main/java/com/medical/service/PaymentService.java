package com.medical.service;

import com.medical.entity.Appointment;
import com.medical.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    public Appointment processPayment(Long appointmentId, String paymentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
            .orElseThrow(() -> new RuntimeException("Appointment not found"));
            
        appointment.setPaymentStatus("COMPLETED");
        appointment.setPaymentId(paymentId);
        
        return appointmentRepository.save(appointment);
    }
    
    public Appointment processRefund(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
            .orElseThrow(() -> new RuntimeException("Appointment not found"));
            
        appointment.setPaymentStatus("REFUNDED");
        
        return appointmentRepository.save(appointment);
    }
} 