package com.police.spring.controller;

import com.police.model.Criminal;
import com.police.spring.service.CriminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Spring REST Controller for Criminal Profiling and Records Management.
 * Implements standard RESTful endpoints for CRUD operations.
 */
@RestController
@RequestMapping("/api/v1/criminals")
@CrossOrigin(origins = "*")
public class CriminalRestController {

    private final CriminalService criminalService;

    @Autowired
    public CriminalRestController(CriminalService criminalService) {
        this.criminalService = criminalService;
    }

    @GetMapping
    public ResponseEntity<List<Criminal>> getAllCriminals() {
        List<Criminal> criminals = criminalService.findAll();
        return ResponseEntity.ok(criminals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Criminal> getCriminalById(@PathVariable("id") String id) {
        Criminal criminal = criminalService.findById(id);
        if (criminal == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(criminal);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Criminal>> searchCriminals(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "id", required = false) String id) {
        List<Criminal> results = criminalService.search(name, id);
        return ResponseEntity.ok(results);
    }

    @PostMapping
    public ResponseEntity<Criminal> createCriminal(@RequestBody Criminal criminal) {
        Criminal saved = criminalService.save(criminal);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCriminal(@PathVariable("id") String id) {
        criminalService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
