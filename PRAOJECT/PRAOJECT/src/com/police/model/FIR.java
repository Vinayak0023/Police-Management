package com.police.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FIR implements Serializable {
    private static final long serialVersionUID = 1L;

    private String firId;
    private String complainantName;
    private String complainantPhone;
    private String complainantAddress;
    private String crimeCategory;
    private String ipcSection;
    private String incidentDate;
    private String incidentLocation;
    private String suspectDetails;
    private String description;
    private String policeStation;
    private String investigatingOfficer;
    private String status; // "Registered", "Under Investigation", "Chargesheeted", "Closed"
    private String registrationDate;

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public FIR() {
        this.status = "Registered";
        this.registrationDate = LocalDateTime.now().format(FORMATTER);
        this.policeStation = "Rajkot Crime Branch HQ";
    }

    public FIR(String firId, String complainantName, String complainantPhone, String complainantAddress,
               String crimeCategory, String ipcSection, String incidentDate, String incidentLocation,
               String suspectDetails, String description, String policeStation, String investigatingOfficer,
               String status) {
        this.firId = firId;
        this.complainantName = complainantName;
        this.complainantPhone = complainantPhone;
        this.complainantAddress = complainantAddress;
        this.crimeCategory = crimeCategory;
        this.ipcSection = ipcSection;
        this.incidentDate = incidentDate;
        this.incidentLocation = incidentLocation;
        this.suspectDetails = suspectDetails;
        this.description = description;
        this.policeStation = policeStation != null ? policeStation : "Rajkot Crime Branch HQ";
        this.investigatingOfficer = investigatingOfficer;
        this.status = status != null ? status : "Registered";
        this.registrationDate = LocalDateTime.now().format(FORMATTER);
    }

    // Getters and Setters
    public String getFirId() { return firId; }
    public void setFirId(String firId) { this.firId = firId; }

    public String getComplainantName() { return complainantName; }
    public void setComplainantName(String complainantName) { this.complainantName = complainantName; }

    public String getComplainantPhone() { return complainantPhone; }
    public void setComplainantPhone(String complainantPhone) { this.complainantPhone = complainantPhone; }

    public String getComplainantAddress() { return complainantAddress; }
    public void setComplainantAddress(String complainantAddress) { this.complainantAddress = complainantAddress; }

    public String getCrimeCategory() { return crimeCategory; }
    public void setCrimeCategory(String crimeCategory) { this.crimeCategory = crimeCategory; }

    public String getIpcSection() { return ipcSection; }
    public void setIpcSection(String ipcSection) { this.ipcSection = ipcSection; }

    public String getIncidentDate() { return incidentDate; }
    public void setIncidentDate(String incidentDate) { this.incidentDate = incidentDate; }

    public String getIncidentLocation() { return incidentLocation; }
    public void setIncidentLocation(String incidentLocation) { this.incidentLocation = incidentLocation; }

    public String getSuspectDetails() { return suspectDetails; }
    public void setSuspectDetails(String suspectDetails) { this.suspectDetails = suspectDetails; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPoliceStation() { return policeStation; }
    public void setPoliceStation(String policeStation) { this.policeStation = policeStation; }

    public String getInvestigatingOfficer() { return investigatingOfficer; }
    public void setInvestigatingOfficer(String investigatingOfficer) { this.investigatingOfficer = investigatingOfficer; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(String registrationDate) { this.registrationDate = registrationDate; }
}
