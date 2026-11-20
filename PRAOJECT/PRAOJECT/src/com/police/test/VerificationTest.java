package com.police.test;

import com.police.data.DatabaseManager;
import com.police.model.CrimeRecord;
import com.police.model.Criminal;

import java.util.List;

public class VerificationTest {
    public static void main(String[] args) {
        System.out.println(">>> Starting Police Management System Backend Verification Test...");

        DatabaseManager db = DatabaseManager.getInstance();
        List<Criminal> all = db.getAllCriminals();
        System.out.println("Total loaded criminals: " + all.size());

        if (all.size() < 3) {
            throw new RuntimeException("Expected at least 3 initial records, got: " + all.size());
        }

        // Test 1: Verify pre-loaded records
        Criminal c1 = db.getCriminalById("RCB-2024-089");
        System.out.println("Verified Criminal 1: " + c1.getName() + " | Photo: " + c1.getPhotoPath() + " | Status: " + c1.getCurrentCustodyStatus());

        Criminal c2 = db.getCriminalById("RCB-2023-042");
        System.out.println("Verified Criminal 2: " + c2.getName() + " | Imprisonment Status: " + c2.getCurrentCustodyStatus() + " (Released: " + c2.isCurrentlyReleased() + ")");

        // Test 2: Search test
        List<Criminal> searchResults = db.searchCriminals("karan", "");
        System.out.println("Search 'karan': Found " + searchResults.size() + " matches.");
        if (searchResults.isEmpty()) {
            throw new RuntimeException("Search failed for 'karan'");
        }

        // Test 3: Add new criminal
        Criminal testNew = new Criminal(
                "RCB-2026-999",
                "Test Offender",
                "28",
                "5'8\"",
                "Ring Road, Rajkot",
                "Screenshot 2026-08-30 110346.png"
        );
        CrimeRecord rec1 = new CrimeRecord(
                "CR-99901",
                "Vehicle Theft",
                "IPC 379",
                "Astron Chowk, Rajkot",
                "None",
                "Solo",
                "01/01/2026",
                "01/01/2027",
                "PSI",
                "R. B. Zala",
                "In Judicial Custody"
        );
        testNew.addCrimeRecord(rec1);
        db.saveCriminal(testNew);

        Criminal retrieved = db.getCriminalById("RCB-2026-999");
        if (retrieved == null) {
            throw new RuntimeException("Failed to retrieve saved criminal!");
        }
        System.out.println("Successfully added and verified new criminal: " + retrieved.getName());

        // Test 4: Add second crime to same criminal
        CrimeRecord rec2 = new CrimeRecord(
                "CR-99902",
                "Illegal Weapon Possession",
                "Arms Act Sec 25",
                "Bhaktinagar, Rajkot",
                "Dhaval Dave",
                "None",
                "01/03/2026",
                "01/03/2029",
                "PI",
                "M. S. Gohil",
                "In Judicial Custody"
        );
        retrieved.addCrimeRecord(rec2);
        db.saveCriminal(retrieved);

        Criminal multiCrime = db.getCriminalById("RCB-2026-999");
        System.out.println("Criminal " + multiCrime.getName() + " now has " + multiCrime.getCrimeRecords().size() + " crime records.");
        if (multiCrime.getCrimeRecords().size() != 2) {
            throw new RuntimeException("Expected 2 crime records for criminal!");
        }

        // Cleanup test record
        db.deleteCriminal("RCB-2026-999");
        System.out.println("Cleaned up test record. Total remaining: " + db.getAllCriminals().size());

        System.out.println(">>> ALL VERIFICATION TESTS PASSED SUCCESSFULLY! ✅");
    }
}
