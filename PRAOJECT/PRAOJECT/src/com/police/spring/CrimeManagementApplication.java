package com.police.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * AJT Module 5: Spring Framework & Spring Boot Application
 * Topic: Crime Management System
 * 
 * Demonstrates:
 * - Inversion of Control (IoC) & ApplicationContext
 * - Dependency Injection (@Autowired)
 * - Spring MVC / REST Controller (@RestController)
 * - Service Layer Architecture (@Service)
 * - Repository Pattern (@Repository)
 */
@SpringBootApplication
public class CrimeManagementApplication {
    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("  STARTING CRIME MANAGEMENT SYSTEM - SPRING BOOT SERVICE");
        System.out.println("==========================================================");
        SpringApplication.run(CrimeManagementApplication.class, args);
    }
}
