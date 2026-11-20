package com.police.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Criminal implements Serializable {
    private static final long serialVersionUID = 1L;

    private String criminalId;
    private String name;
    private String age;
    private String height;
    private String address;
    private String photoPath;
    private List<CrimeRecord> crimeRecords;

    public Criminal() {
        this.crimeRecords = new ArrayList<>();
    }

    public Criminal(String criminalId, String name, String age, String height, String address, String photoPath) {
        this.criminalId = criminalId;
        this.name = name;
        this.age = age;
        this.height = height;
        this.address = address;
        this.photoPath = photoPath;
        this.crimeRecords = new ArrayList<>();
    }

    public void addCrimeRecord(CrimeRecord record) {
        if (this.crimeRecords == null) {
            this.crimeRecords = new ArrayList<>();
        }
        this.crimeRecords.add(record);
    }

    public CrimeRecord getLatestCrime() {
        if (crimeRecords != null && !crimeRecords.isEmpty()) {
            return crimeRecords.get(crimeRecords.size() - 1);
        }
        return null;
    }

    public boolean isCurrentlyReleased() {
        CrimeRecord latest = getLatestCrime();
        if (latest != null) {
            return latest.isReleased();
        }
        return true;
    }

    public String getCurrentCustodyStatus() {
        CrimeRecord latest = getLatestCrime();
        if (latest != null) {
            return latest.getCustodyStatus();
        }
        return "Unknown";
    }

    // Getters and Setters
    public String getCriminalId() { return criminalId; }
    public void setCriminalId(String criminalId) { this.criminalId = criminalId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }

    public String getHeight() { return height; }
    public void setHeight(String height) { this.height = height; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhotoPath() { return photoPath; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }

    public List<CrimeRecord> getCrimeRecords() { 
        if (crimeRecords == null) {
            crimeRecords = new ArrayList<>();
        }
        return crimeRecords; 
    }
    public void setCrimeRecords(List<CrimeRecord> crimeRecords) { this.crimeRecords = crimeRecords; }
}
