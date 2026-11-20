package com.police.spring.service;

import com.police.data.DatabaseManager;
import com.police.model.Criminal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CriminalService {

    public List<Criminal> findAll() {
        return DatabaseManager.getInstance().getAllCriminals();
    }

    public Criminal findById(String id) {
        return DatabaseManager.getInstance().getCriminalById(id);
    }

    public List<Criminal> search(String name, String id) {
        return DatabaseManager.getInstance().searchCriminals(name, id);
    }

    public Criminal save(Criminal criminal) {
        if (criminal.getCriminalId() == null || criminal.getCriminalId().trim().isEmpty()) {
            criminal.setCriminalId(DatabaseManager.getInstance().generateNextCriminalId());
        }
        DatabaseManager.getInstance().saveCriminal(criminal);
        return criminal;
    }

    public void deleteById(String id) {
        DatabaseManager.getInstance().deleteCriminal(id);
    }
}
