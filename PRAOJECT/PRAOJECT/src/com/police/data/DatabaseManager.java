package com.police.data;

import com.police.model.CrimeRecord;
import com.police.model.Criminal;
import com.police.model.FIR;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DATA_FILE = "police_records.json";
    private static final String FIR_DATA_FILE = "fir_records.json";
    private static DatabaseManager instance;
    private final List<Criminal> criminals;
    private final List<FIR> firs;

    private DatabaseManager() {
        criminals = new ArrayList<>();
        firs = new ArrayList<>();
        loadData();
        loadFIRData();
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    // ================= Criminal Management =================

    public synchronized List<Criminal> getAllCriminals() {
        return new ArrayList<>(criminals);
    }

    public synchronized Criminal getCriminalById(String id) {
        if (id == null) return null;
        for (Criminal c : criminals) {
            if (c.getCriminalId().equalsIgnoreCase(id.trim())) {
                return c;
            }
        }
        return null;
    }

    public synchronized List<Criminal> searchCriminals(String nameQuery, String idQuery) {
        List<Criminal> results = new ArrayList<>();
        String nameFilter = (nameQuery == null) ? "" : nameQuery.trim().toLowerCase();
        String idFilter = (idQuery == null) ? "" : idQuery.trim().toLowerCase();

        for (Criminal c : criminals) {
            boolean matchesName = nameFilter.isEmpty() || (c.getName() != null && c.getName().toLowerCase().contains(nameFilter));
            boolean matchesId = idFilter.isEmpty() || (c.getCriminalId() != null && c.getCriminalId().toLowerCase().contains(idFilter));

            if (matchesName && matchesId) {
                results.add(c);
            }
        }
        return results;
    }

    public synchronized void saveCriminal(Criminal criminal) {
        if (criminal == null) return;
        boolean exists = false;
        for (int i = 0; i < criminals.size(); i++) {
            if (criminals.get(i).getCriminalId().equalsIgnoreCase(criminal.getCriminalId())) {
                criminals.set(i, criminal);
                exists = true;
                break;
            }
        }
        if (!exists) {
            criminals.add(criminal);
        }
        persistData();
    }

    public synchronized void deleteCriminal(String id) {
        criminals.removeIf(c -> c.getCriminalId().equalsIgnoreCase(id));
        persistData();
    }

    public synchronized String generateNextCriminalId() {
        int maxNum = 100;
        for (Criminal c : criminals) {
            String id = c.getCriminalId();
            if (id != null && id.startsWith("RCB-2026-")) {
                try {
                    int num = Integer.parseInt(id.substring("RCB-2026-".length()));
                    if (num > maxNum) maxNum = num;
                } catch (NumberFormatException ignored) {}
            }
        }
        return String.format("RCB-2026-%03d", maxNum + 1);
    }

    // ================= FIR Management =================

    public synchronized List<FIR> getAllFIRs() {
        return new ArrayList<>(firs);
    }

    public synchronized FIR getFIRById(String firId) {
        if (firId == null) return null;
        for (FIR f : firs) {
            if (f.getFirId().equalsIgnoreCase(firId.trim())) {
                return f;
            }
        }
        return null;
    }

    public synchronized List<FIR> searchFIRs(String query, String statusFilter) {
        List<FIR> results = new ArrayList<>();
        String q = (query == null) ? "" : query.trim().toLowerCase();
        String st = (statusFilter == null) ? "" : statusFilter.trim().toLowerCase();

        for (FIR f : firs) {
            boolean matchesQuery = q.isEmpty()
                    || (f.getFirId() != null && f.getFirId().toLowerCase().contains(q))
                    || (f.getComplainantName() != null && f.getComplainantName().toLowerCase().contains(q))
                    || (f.getCrimeCategory() != null && f.getCrimeCategory().toLowerCase().contains(q))
                    || (f.getIpcSection() != null && f.getIpcSection().toLowerCase().contains(q));
            boolean matchesStatus = st.isEmpty() || st.equals("all")
                    || (f.getStatus() != null && f.getStatus().toLowerCase().contains(st));

            if (matchesQuery && matchesStatus) {
                results.add(f);
            }
        }
        return results;
    }

    public synchronized void saveFIR(FIR fir) {
        if (fir == null) return;
        boolean exists = false;
        for (int i = 0; i < firs.size(); i++) {
            if (firs.get(i).getFirId().equalsIgnoreCase(fir.getFirId())) {
                firs.set(i, fir);
                exists = true;
                break;
            }
        }
        if (!exists) {
            firs.add(0, fir); // newest on top
        }
        persistFIRData();
    }

    public synchronized String generateNextFIRId() {
        int maxNum = 0;
        for (FIR f : firs) {
            String id = f.getFirId();
            if (id != null && id.startsWith("FIR-2026-")) {
                try {
                    int num = Integer.parseInt(id.substring("FIR-2026-".length()));
                    if (num > maxNum) maxNum = num;
                } catch (NumberFormatException ignored) {}
            }
        }
        return String.format("FIR-2026-%03d", maxNum + 1);
    }

    // ================= Persistence =================

    private void loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            initSampleData();
            persistData();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            parseJson(sb.toString());
            if (criminals.isEmpty()) {
                initSampleData();
                persistData();
            }
        } catch (Exception e) {
            System.err.println("Error reading database file: " + e.getMessage());
            initSampleData();
        }
    }

    private void persistData() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(DATA_FILE), StandardCharsets.UTF_8))) {
            writer.write(toJson());
        } catch (IOException e) {
            System.err.println("Error saving database file: " + e.getMessage());
        }
    }

    private void loadFIRData() {
        File file = new File(FIR_DATA_FILE);
        if (!file.exists()) {
            initSampleFIRData();
            persistFIRData();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            parseFIRJson(sb.toString());
            if (firs.isEmpty()) {
                initSampleFIRData();
                persistFIRData();
            }
        } catch (Exception e) {
            System.err.println("Error reading FIR file: " + e.getMessage());
            initSampleFIRData();
        }
    }

    private void persistFIRData() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FIR_DATA_FILE), StandardCharsets.UTF_8))) {
            writer.write(toFIRJson());
        } catch (IOException e) {
            System.err.println("Error saving FIR file: " + e.getMessage());
        }
    }

    private void initSampleData() {
        criminals.clear();

        // 1. Karan Jadeja
        Criminal c1 = new Criminal("RCB-2024-089", "Karan Devraj Jadeja", "32", "5'11\" (180 cm)", "Block C-14, Green City, 150ft Ring Road, Rajkot", "Screenshot 2026-08-30 110346.png");
        CrimeRecord r1 = new CrimeRecord("CR-8901", "Armed Extortion & Assault", "IPC 384, 386, 324 (Extortion by putting a person in fear of death)", "Yagnik Road, Rajkot", "Pravin Solanki, Dilip Gohel", "Saurashtra Syndicate", "12/03/2024", "15/12/2027", "SP", "Vikram Rathore", "In Judicial Custody");
        c1.addCrimeRecord(r1);
        criminals.add(c1);

        // 2. Vikram Sinh Vaghela (Released)
        Criminal c2 = new Criminal("RCB-2023-042", "Vikram Sinh Vaghela", "36", "6'0\" (183 cm)", "Plot 78, Rajput Para Street 4, Rajkot", "Screenshot 2026-08-30 110406.png");
        CrimeRecord r2a = new CrimeRecord("CR-4201", "Bank Robbery & Arms Act", "IPC 395, 397 (Dacoity with deadly weapon), Arms Act Sec 25", "Dhebar Road, Rajkot", "Jaydeepsinh Zala", "Vaghela Brothers Gang", "10/01/2023", "10/01/2025", "SSP", "A. K. Sharma", "Released");
        c2.addCrimeRecord(r2a);
        criminals.add(c2);

        // 3. Asif Mohammad Sheikh (In Custody)
        Criminal c3 = new Criminal("RCB-2025-115", "Asif Mohammad Sheikh", "29", "5'9\" (175 cm)", "Near Raiya Telephone Exchange, Raiya Road, Rajkot", "Screenshot 2026-08-30 110427.png");
        CrimeRecord r3 = new CrimeRecord("CR-1151", "Cyber Extortion & Hawala Fraud", "IPC 420, 120B & IT Act Sec 66D (Cheating by personation using computer)", "Gondal Road Cyber Hub, Rajkot", "Riyaz Memon", "Digital Shadow Network", "05/08/2025", "05/08/2028", "PI", "K. L. Parmar", "In Judicial Custody");
        c3.addCrimeRecord(r3);
        criminals.add(c3);
    }

    private void initSampleFIRData() {
        firs.clear();

        FIR f1 = new FIR(
                "FIR-2026-001",
                "Ramesh K. Trivedi",
                "+91 9825123456",
                "Tagore Road, Rajkot",
                "Armed Robbery",
                "IPC 392, 397",
                "24/10/2026",
                "Tagore Road Jewelry Market, Rajkot",
                "Two unidentified masked men riding black Pulsar",
                "Two assailants barged into shop, threatened staff with country-made pistol, and looted ornaments worth Rs. 8,50,000.",
                "Rajkot Crime Branch HQ",
                "PI M. S. Gohil",
                "Under Investigation"
        );
        firs.add(f1);

        FIR f2 = new FIR(
                "FIR-2026-002",
                "Sunita M. Patel",
                "+91 9426876543",
                "150ft Ring Road, Rajkot",
                "Cyber Financial Fraud",
                "IPC 420, IT Act Sec 66D",
                "18/10/2026",
                "Online Banking Transaction",
                "Linked to syndicate associated with Asif Sheikh",
                "Complainant was deceived by phishing SMS purporting to update electricity KYC, resulting in unauthorized debit of Rs 4,50,000.",
                "Cyber Crime Police Station, Rajkot",
                "PSI R. B. Zala",
                "Chargesheeted"
        );
        firs.add(f2);

        FIR f3 = new FIR(
                "FIR-2026-003",
                "Hitesh D. Shah",
                "+91 9909234567",
                "Dhebar Road, Rajkot",
                "Criminal Intimidation & Extortion",
                "IPC 384, 506",
                "02/10/2026",
                "Commercial Complex, Dhebar Road",
                "Associates of Karan Devraj Jadeja",
                "Complainant received extortion threats demanding Rs. 10 Lakhs protection fee. Audio recordings submitted as evidence.",
                "Rajkot Crime Branch HQ",
                "SP Vikram Rathore",
                "Closed"
        );
        firs.add(f3);
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
    }

    private String unescape(String s) {
        if (s == null) return "";
        return s.replace("\\\"", "\"").replace("\\\\", "\\").replace("\\n", "\n");
    }

    private String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < criminals.size(); i++) {
            Criminal c = criminals.get(i);
            sb.append("  {\n");
            sb.append("    \"criminalId\": \"").append(escape(c.getCriminalId())).append("\",\n");
            sb.append("    \"name\": \"").append(escape(c.getName())).append("\",\n");
            sb.append("    \"age\": \"").append(escape(c.getAge())).append("\",\n");
            sb.append("    \"height\": \"").append(escape(c.getHeight())).append("\",\n");
            sb.append("    \"address\": \"").append(escape(c.getAddress())).append("\",\n");
            sb.append("    \"photoPath\": \"").append(escape(c.getPhotoPath())).append("\",\n");
            sb.append("    \"crimeRecords\": [\n");
            List<CrimeRecord> records = c.getCrimeRecords();
            for (int j = 0; j < records.size(); j++) {
                CrimeRecord r = records.get(j);
                sb.append("      {\n");
                sb.append("        \"recordId\": \"").append(escape(r.getRecordId())).append("\",\n");
                sb.append("        \"reasonForArrest\": \"").append(escape(r.getReasonForArrest())).append("\",\n");
                sb.append("        \"implementedSection\": \"").append(escape(r.getImplementedSection())).append("\",\n");
                sb.append("        \"crimeLocation\": \"").append(escape(r.getCrimeLocation())).append("\",\n");
                sb.append("        \"partnerNames\": \"").append(escape(r.getPartnerNames())).append("\",\n");
                sb.append("        \"gangName\": \"").append(escape(r.getGangName())).append("\",\n");
                sb.append("        \"imprisonmentFrom\": \"").append(escape(r.getImprisonmentFrom())).append("\",\n");
                sb.append("        \"imprisonmentTo\": \"").append(escape(r.getImprisonmentTo())).append("\",\n");
                sb.append("        \"officerRank\": \"").append(escape(r.getOfficerRank())).append("\",\n");
                sb.append("        \"officerName\": \"").append(escape(r.getOfficerName())).append("\",\n");
                sb.append("        \"custodyStatus\": \"").append(escape(r.getCustodyStatus())).append("\"\n");
                sb.append("      }").append(j < records.size() - 1 ? "," : "").append("\n");
            }
            sb.append("    ]\n");
            sb.append("  }").append(i < criminals.size() - 1 ? "," : "").append("\n");
        }
        sb.append("]\n");
        return sb.toString();
    }

    private void parseJson(String json) {
        criminals.clear();
        if (json == null || json.trim().isEmpty()) return;

        int pos = 0;
        while (pos < json.length()) {
            int crimStart = json.indexOf('{', pos);
            if (crimStart == -1) break;

            int crimEnd = findMatchingBrace(json, crimStart);
            if (crimEnd == -1) break;

            String crimBlock = json.substring(crimStart, crimEnd + 1);
            Criminal criminal = parseCriminalBlock(crimBlock);
            if (criminal != null && criminal.getCriminalId() != null) {
                criminals.add(criminal);
            }

            pos = crimEnd + 1;
        }
    }

    private Criminal parseCriminalBlock(String block) {
        String criminalId = extractField(block, "criminalId");
        String name = extractField(block, "name");
        String age = extractField(block, "age");
        String height = extractField(block, "height");
        String address = extractField(block, "address");
        String photoPath = extractField(block, "photoPath");

        Criminal c = new Criminal(criminalId, name, age, height, address, photoPath);

        int recsStart = block.indexOf("\"crimeRecords\"");
        if (recsStart != -1) {
            int arrStart = block.indexOf('[', recsStart);
            int arrEnd = block.indexOf(']', arrStart);
            if (arrStart != -1 && arrEnd != -1) {
                String recordsSub = block.substring(arrStart, arrEnd + 1);
                int rPos = 0;
                while (rPos < recordsSub.length()) {
                    int rStart = recordsSub.indexOf('{', rPos);
                    if (rStart == -1) break;
                    int rEnd = findMatchingBrace(recordsSub, rStart);
                    if (rEnd == -1) break;

                    String rBlock = recordsSub.substring(rStart, rEnd + 1);
                    CrimeRecord rec = new CrimeRecord(
                            extractField(rBlock, "recordId"),
                            extractField(rBlock, "reasonForArrest"),
                            extractField(rBlock, "implementedSection"),
                            extractField(rBlock, "crimeLocation"),
                            extractField(rBlock, "partnerNames"),
                            extractField(rBlock, "gangName"),
                            extractField(rBlock, "imprisonmentFrom"),
                            extractField(rBlock, "imprisonmentTo"),
                            extractField(rBlock, "officerRank"),
                            extractField(rBlock, "officerName"),
                            extractField(rBlock, "custodyStatus")
                    );
                    c.addCrimeRecord(rec);
                    rPos = rEnd + 1;
                }
            }
        }

        return c;
    }

    private String toFIRJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < firs.size(); i++) {
            FIR f = firs.get(i);
            sb.append("  {\n");
            sb.append("    \"firId\": \"").append(escape(f.getFirId())).append("\",\n");
            sb.append("    \"complainantName\": \"").append(escape(f.getComplainantName())).append("\",\n");
            sb.append("    \"complainantPhone\": \"").append(escape(f.getComplainantPhone())).append("\",\n");
            sb.append("    \"complainantAddress\": \"").append(escape(f.getComplainantAddress())).append("\",\n");
            sb.append("    \"crimeCategory\": \"").append(escape(f.getCrimeCategory())).append("\",\n");
            sb.append("    \"ipcSection\": \"").append(escape(f.getIpcSection())).append("\",\n");
            sb.append("    \"incidentDate\": \"").append(escape(f.getIncidentDate())).append("\",\n");
            sb.append("    \"incidentLocation\": \"").append(escape(f.getIncidentLocation())).append("\",\n");
            sb.append("    \"suspectDetails\": \"").append(escape(f.getSuspectDetails())).append("\",\n");
            sb.append("    \"description\": \"").append(escape(f.getDescription())).append("\",\n");
            sb.append("    \"policeStation\": \"").append(escape(f.getPoliceStation())).append("\",\n");
            sb.append("    \"investigatingOfficer\": \"").append(escape(f.getInvestigatingOfficer())).append("\",\n");
            sb.append("    \"status\": \"").append(escape(f.getStatus())).append("\",\n");
            sb.append("    \"registrationDate\": \"").append(escape(f.getRegistrationDate())).append("\"\n");
            sb.append("  }").append(i < firs.size() - 1 ? "," : "").append("\n");
        }
        sb.append("]\n");
        return sb.toString();
    }

    private void parseFIRJson(String json) {
        firs.clear();
        if (json == null || json.trim().isEmpty()) return;

        int pos = 0;
        while (pos < json.length()) {
            int start = json.indexOf('{', pos);
            if (start == -1) break;

            int end = findMatchingBrace(json, start);
            if (end == -1) break;

            String block = json.substring(start, end + 1);
            String firId = extractField(block, "firId");
            if (!firId.isEmpty()) {
                FIR f = new FIR(
                        firId,
                        extractField(block, "complainantName"),
                        extractField(block, "complainantPhone"),
                        extractField(block, "complainantAddress"),
                        extractField(block, "crimeCategory"),
                        extractField(block, "ipcSection"),
                        extractField(block, "incidentDate"),
                        extractField(block, "incidentLocation"),
                        extractField(block, "suspectDetails"),
                        extractField(block, "description"),
                        extractField(block, "policeStation"),
                        extractField(block, "investigatingOfficer"),
                        extractField(block, "status")
                );
                String regDate = extractField(block, "registrationDate");
                if (!regDate.isEmpty()) {
                    f.setRegistrationDate(regDate);
                }
                firs.add(f);
            }
            pos = end + 1;
        }
    }

    private int findMatchingBrace(String s, int start) {
        int depth = 0;
        boolean inQuotes = false;
        for (int i = start; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\"' && (i == 0 || s.charAt(i - 1) != '\\')) {
                inQuotes = !inQuotes;
            } else if (!inQuotes) {
                if (c == '{') depth++;
                else if (c == '}') {
                    depth--;
                    if (depth == 0) return i;
                }
            }
        }
        return -1;
    }

    private String extractField(String json, String field) {
        String key = "\"" + field + "\"";
        int idx = json.indexOf(key);
        if (idx == -1) return "";
        int colon = json.indexOf(':', idx + key.length());
        if (colon == -1) return "";
        int quoteStart = json.indexOf('\"', colon);
        if (quoteStart == -1) return "";

        StringBuilder sb = new StringBuilder();
        for (int i = quoteStart + 1; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '\"' && json.charAt(i - 1) != '\\') {
                break;
            }
            sb.append(c);
        }
        return unescape(sb.toString());
    }
}
