package com.sena.hospital.controller;


import com.sena.hospital.model.Medic;
import com.sena.hospital.repository.MedicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medics")
public class MedicController {

    @Autowired
    private MedicRepository medicRepository;

    @GetMapping
    public List<Medic> getAllMedics() {
        return medicRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medic> getMedicById(@PathVariable Long id) {
        Optional<Medic> medic = medicRepository.findById(id);
        if (medic.isPresent()) {
            return ResponseEntity.ok(medic.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Medic createMedic(@RequestBody Medic medic) {
        return medicRepository.save(medic);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medic> updateMedic(@PathVariable Long id, @RequestBody Medic medicDetails) {
        Optional<Medic> medic = medicRepository.findById(id);
        if (medic.isPresent()) {
            Medic updatedMedic = medic.get();
            updatedMedic.setFirstname(medicDetails.getFirstname());
            updatedMedic.setLastname(medicDetails.getLastname());
            updatedMedic.setSpecialty(medicDetails.getSpecialty());
            updatedMedic.setPhone(medicDetails.getPhone());
            return ResponseEntity.ok(medicRepository.save(updatedMedic));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedic(@PathVariable Long id) {
        Optional<Medic> medic = medicRepository.findById(id);
        if (medic.isPresent()) {
            medicRepository.delete(medic.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
