package com.sena.hospital.controller;

import com.sena.hospital.model.Appointment;
import com.sena.hospital.model.Patient;
import com.sena.hospital.model.Medic;
import com.sena.hospital.repository.AppointmentRepository;
import com.sena.hospital.repository.PatientRepository;
import com.sena.hospital.repository.MedicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    private  AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedicRepository medicRepository;

    @GetMapping
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if (appointment.isPresent()){
            return  ResponseEntity.ok(appointment.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        Optional<Patient> patient = patientRepository.findById(appointment.getPatient().getId());
        Optional<Medic> medic = medicRepository.findById(appointment.getMedic().getId());

        if (patient.isPresent() && medic.isPresent()) {
            appointment.setPatient(patient.get());
            appointment.setMedic(medic.get());
            return ResponseEntity.ok(appointmentRepository.save(appointment));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @RequestBody Appointment AppointmentDetails) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if (appointment.isPresent()) {
            Appointment updatedAppointment = appointment.get();
            updatedAppointment.setDate(AppointmentDetails.getDate());
            updatedAppointment.setTime(AppointmentDetails.getTime());
            return ResponseEntity.ok(appointmentRepository.save(updatedAppointment));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCita(@PathVariable Long id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if (appointment.isPresent()) {
            appointmentRepository.delete(appointment.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
