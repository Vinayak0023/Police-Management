package com.police.spring.controller;

import com.police.model.FIR;
import com.police.spring.service.FIRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Spring REST Controller for First Information Report (FIR) Legal Tracking.
 */
@RestController
@RequestMapping("/api/v1/firs")
@CrossOrigin(origins = "*")
public class FIRRestController {

    private final FIRService firService;

    @Autowired
    public FIRRestController(FIRService firService) {
        this.firService = firService;
    }

    @GetMapping
    public ResponseEntity<List<FIR>> getAllFIRs() {
        return ResponseEntity.ok(firService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FIR> getFIRById(@PathVariable("id") String id) {
        FIR fir = firService.findById(id);
        if (fir == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(fir);
    }

    @PostMapping
    public ResponseEntity<FIR> registerFIR(@RequestBody FIR fir) {
        FIR created = firService.create(fir);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<FIR> updateStatus(@PathVariable("id") String id, @RequestParam("status") String status) {
        FIR updated = firService.updateStatus(id, status);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updated);
    }
}
