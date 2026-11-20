# Police Management System - Rajkot Crime Branch

A comprehensive Crime & Police Management System built for **Rajkot Crime Branch (Gujarat Police)** featuring both a **Java Swing Desktop Application** and a **Web Interface & REST API**.

---

## 📁 Project Folder & File Structure

The project files and source code are located in: **`B:\PRAOJECT\PRAOJECT\`**

```text
B:\PRAOJECT\PRAOJECT\
│
├── run.bat                          # Double-click to compile & run Java Swing UI
├── run.ps1                          # PowerShell startup script
├── police_records.json              # Criminal records database (JSON format)
├── fir_records.json                 # First Information Report (FIR) database
├── README.md                        # Documentation & setup guide
│
├── screenshots/                     # UI screenshots & visual verification
│
├── src/com/police/                  # Java Source Code
│   ├── Main.java                    # Entry point for the Swing Desktop App
│   │
│   ├── model/                       # Data Entities
│   │   ├── Criminal.java            # Criminal model (ID, Name, Age, Photo, Records)
│   │   ├── CrimeRecord.java         # Incident details, law sections, custody status
│   │   └── FIR.java                 # FIR details, complainant, suspect, IPC sections
│   │
│   ├── data/
│   │   └── DatabaseManager.java     # JSON persistence, queries & auto-ID generation
│   │
│   ├── ui/                          # Java Swing User Interface
│   │   ├── PoliceTheme.java         # Themes, police color palette, button styling
│   │   ├── StartupDialog.java       # Initial PopUp: "Rajkot Crime Branch"
│   │   ├── MainFrame.java           # Main application frame & navigation
│   │   ├── Page1Search.java         # Page 1: Criminal records search & table
│   │   ├── Page2CriminalDetail.java # Page 2: Dossier, mugshot, released/custody badge
│   │   ├── Page3AddCriminal.java    # Page 3: New criminal registration & photo upload
│   │   └── Page4FIRManagement.java  # Page 4: FIR registration & FIR log table
│   │
│   ├── web/                         # Web Server & Servlets
│   │   ├── CrimeWebServer.java      # Embedded web server implementation
│   │   └── servlets/
│   │       └── AuthFilter.java      # Authentication & route authorization
│   │
│   ├── spring/                      # Spring Boot & REST APIs
│   │   ├── CrimeManagementApplication.java
│   │   ├── controller/
│   │   │   ├── CriminalRestController.java
│   │   │   └── FIRRestController.java
│   │   └── service/
│   │       ├── CriminalService.java
│   │       └── FIRService.java
│   │
│   └── test/
│       ├── VerificationTest.java    # Automated backend verification test
│       └── CaptureScreenshots.java  # UI screenshot capture automation
│
└── web/                             # Web Frontend & JSPs
    ├── views/
    │   ├── index.jsp                # Web portal home
    │   ├── dashboard.jsp            # Crime branch dashboard
    │   ├── criminals.jsp            # Criminals list view
    │   ├── criminal-detail.jsp      # Individual criminal profile
    │   ├── fir-list.jsp             # FIR table view
    │   └── fir-register.jsp         # FIR entry form
    └── WEB-INF/
        └── web.xml                  # Servlet configurations
```

---

## 🚀 How to Run

### 1. Java Swing Desktop App (Recommended)
Double-click `run.bat` in `B:\PRAOJECT\PRAOJECT` or run via PowerShell:
```powershell
cd B:\PRAOJECT\PRAOJECT
.\run.ps1
```

Or run directly using standard Java:
```powershell
cd B:\PRAOJECT\PRAOJECT
javac -encoding UTF-8 -d bin (Get-ChildItem -Recurse -Filter *.java src | Select-Object -ExpandProperty FullName)
java -cp bin com.police.Main
```

### 2. Run Backend Tests
```powershell
cd B:\PRAOJECT\PRAOJECT
java -cp bin com.police.test.VerificationTest
```

---

## 🏛️ Key Capabilities

1. **PopUp Opening**: Displays *"Police Management System - Rajkot Crime Branch"*.
2. **Page 1 - Criminal Records**: Mandatory name search, ID search, clear, table view of active records.
3. **Page 2 - Criminal Profile**: Mugshot display, custody status indicator (🟢 Released / 🔴 In Judicial Custody), IPC law sections, in-charge officers, and multi-offense recording via Edit.
4. **Page 3 - New Registration**: First-time offender registration with photo selection and biometric metadata.
5. **Page 4 - FIR Management**: Register and inspect official FIR records.
