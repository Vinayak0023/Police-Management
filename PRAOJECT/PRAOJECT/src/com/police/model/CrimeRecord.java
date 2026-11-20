package com.police.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CrimeRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private String recordId;
    private String reasonForArrest;
    private String implementedSection;
    private String crimeLocation;
    private String partnerNames;
    private String gangName;
    private String imprisonmentFrom; // DD/MM/YYYY
    private String imprisonmentTo;   // DD/MM/YYYY
    private String officerRank;      // DGP, ADGP, IGP, SSP, SP, SI, PI, PSI, DSP
    private String officerName;
    private String custodyStatus;    // "In Judicial Custody" or "Released"

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public CrimeRecord() {
        this.recordId = "CR-" + System.currentTimeMillis() % 100000;
        this.custodyStatus = "In Judicial Custody";
        this.officerRank = "PI";
    }

    public CrimeRecord(String recordId, String reasonForArrest, String implementedSection,
                       String crimeLocation, String partnerNames, String gangName,
                       String imprisonmentFrom, String imprisonmentTo,
                       String officerRank, String officerName, String custodyStatus) {
        this.recordId = recordId;
        this.reasonForArrest = reasonForArrest;
        this.implementedSection = implementedSection;
        this.crimeLocation = crimeLocation;
        this.partnerNames = partnerNames;
        this.gangName = gangName;
        this.imprisonmentFrom = imprisonmentFrom;
        this.imprisonmentTo = imprisonmentTo;
        this.officerRank = officerRank;
        this.officerName = officerName;
        this.custodyStatus = custodyStatus;
        autoUpdateCustodyStatus();
    }

    public void autoUpdateCustodyStatus() {
        if (imprisonmentTo != null && !imprisonmentTo.trim().isEmpty()) {
            try {
                LocalDate toDate = LocalDate.parse(imprisonmentTo.trim(), DATE_FORMATTER);
                if (toDate.isBefore(LocalDate.now())) {
                    this.custodyStatus = "Released";
                } else {
                    this.custodyStatus = "In Judicial Custody";
                }
            } catch (DateTimeParseException ignored) {
                // Keep existing custodyStatus if format differs
            }
        }
    }

    public boolean isReleased() {
        if ("Released".equalsIgnoreCase(custodyStatus)) {
            return true;
        }
        if (imprisonmentTo != null && !imprisonmentTo.trim().isEmpty()) {
            try {
                LocalDate toDate = LocalDate.parse(imprisonmentTo.trim(), DATE_FORMATTER);
                return toDate.isBefore(LocalDate.now());
            } catch (DateTimeParseException ignored) {
            }
        }
        return false;
    }

    // Getters and Setters
    public String getRecordId() { return recordId; }
    public void setRecordId(String recordId) { this.recordId = recordId; }

    public String getReasonForArrest() { return reasonForArrest; }
    public void setReasonForArrest(String reasonForArrest) { this.reasonForArrest = reasonForArrest; }

    public String getImplementedSection() { return implementedSection; }
    public void setImplementedSection(String implementedSection) { this.implementedSection = implementedSection; }

    public String getCrimeLocation() { return crimeLocation; }
    public void setCrimeLocation(String crimeLocation) { this.crimeLocation = crimeLocation; }

    public String getPartnerNames() { return partnerNames; }
    public void setPartnerNames(String partnerNames) { this.partnerNames = partnerNames; }

    public String getGangName() { return gangName; }
    public void setGangName(String gangName) { this.gangName = gangName; }

    public String getImprisonmentFrom() { return imprisonmentFrom; }
    public void setImprisonmentFrom(String imprisonmentFrom) { 
        this.imprisonmentFrom = imprisonmentFrom; 
    }

    public String getImprisonmentTo() { return imprisonmentTo; }
    public void setImprisonmentTo(String imprisonmentTo) { 
        this.imprisonmentTo = imprisonmentTo; 
        autoUpdateCustodyStatus();
    }

    public String getOfficerRank() { return officerRank; }
    public void setOfficerRank(String officerRank) { this.officerRank = officerRank; }

    public String getOfficerName() { return officerName; }
    public void setOfficerName(String officerName) { this.officerName = officerName; }

    public String getCustodyStatus() { return custodyStatus; }
    public void setCustodyStatus(String custodyStatus) { this.custodyStatus = custodyStatus; }

    public String getFullOfficerInCharge() {
        if (officerRank == null || officerRank.trim().isEmpty()) {
            return officerName == null ? "" : officerName;
        }
        return officerRank + " " + (officerName == null ? "" : officerName);
    }
}
