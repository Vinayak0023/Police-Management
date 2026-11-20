package com.police.web.servlets;

import java.util.HashMap;
import java.util.Map;

/**
 * AJT Module 3: Servlet Filter Demonstration
 * AuthFilter checks authentication state for protected crime management routes.
 */
public class AuthFilter {
    private static final Map<String, String> activeSessions = new HashMap<>();

    public static synchronized void createSession(String sessionId, String username, String role) {
        activeSessions.put(sessionId, username + ":" + role);
    }

    public static synchronized boolean isValidSession(String sessionId) {
        return sessionId != null && activeSessions.containsKey(sessionId);
    }

    public static synchronized String getSessionUser(String sessionId) {
        String data = activeSessions.get(sessionId);
        if (data != null && data.contains(":")) {
            return data.split(":")[0];
        }
        return "Officer";
    }

    public static synchronized String getSessionRole(String sessionId) {
        String data = activeSessions.get(sessionId);
        if (data != null && data.contains(":")) {
            return data.split(":")[1];
        }
        return "Inspector";
    }

    public static synchronized void invalidateSession(String sessionId) {
        if (sessionId != null) {
            activeSessions.remove(sessionId);
        }
    }
}
