package com.police.spring.service;

import com.police.data.DatabaseManager;
import com.police.model.FIR;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FIRService {

    public List<FIR> findAll() {
        return DatabaseManager.getInstance().getAllFIRs();
    }

    public FIR findById(String id) {
        return DatabaseManager.getInstance().getFIRById(id);
    }

    public FIR create(FIR fir) {
        if (fir.getFirId() == null || fir.getFirId().trim().isEmpty()) {
            fir.setFirId(DatabaseManager.getInstance().generateNextFIRId());
        }
        DatabaseManager.getInstance().saveFIR(fir);
        return fir;
    }

    public FIR updateStatus(String firId, String newStatus) {
        FIR fir = DatabaseManager.getInstance().getFIRById(firId);
        if (fir != null) {
            fir.setStatus(newStatus);
            DatabaseManager.getInstance().saveFIR(fir);
            return fir;
        }
        return null;
    }
}
