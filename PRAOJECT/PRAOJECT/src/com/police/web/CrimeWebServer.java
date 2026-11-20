package com.police.web;

import com.police.data.DatabaseManager;
import com.police.model.CrimeRecord;
import com.police.model.Criminal;
import com.police.model.FIR;
import com.police.web.servlets.AuthFilter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class CrimeWebServer {
    private static final int PORT = 8080;
    private static final String VIEWS_DIR = "web/views/";

    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
            System.out.println("==================================================================");
            System.out.println("   RAJKOT CRIME BRANCH - AJT ENTERPRISE WEB SERVER (PORT " + PORT + ")");
            System.out.println("   Technologies: Java Servlet, JSP, Spring REST API, JDBC / JSON");
            System.out.println("==================================================================");

            // 1. Web Page Routes (JSP Views & Servlets)
            server.createContext("/", new IndexHandler());
            server.createContext("/login", new LoginServletHandler());
            server.createContext("/dashboard", new DashboardHandler());
            server.createContext("/criminals", new CriminalsHandler());
            server.createContext("/criminal-detail", new CriminalDetailHandler());
            server.createContext("/firs", new FIRHandler());
            server.createContext("/fir-register", new FIRRegisterHandler());
            server.createContext("/logout", new LogoutHandler());

            // 2. Image Asset Handlers (Mugshots)
            server.createContext("/images/front", new ImageHandler("Screenshot 2026-08-30 110346.png"));
            server.createContext("/images/left", new ImageHandler("Screenshot 2026-08-30 110406.png"));
            server.createContext("/images/right", new ImageHandler("Screenshot 2026-08-30 110427.png"));

            // 3. Spring-style REST API Endpoints
            server.createContext("/api/criminals", new RestCriminalsHandler());
            server.createContext("/api/firs", new RestFIRsHandler());
            server.createContext("/api/analytics/summary", new RestAnalyticsHandler());

            server.setExecutor(null); // default multithreaded executor
            server.start();

            System.out.println(">>> Crime Management Web Portal live at: http://localhost:" + PORT + "/");
            System.out.println(">>> Spring REST API live at: http://localhost:" + PORT + "/api/criminals");
            System.out.println(">>> Press Ctrl+C in console to stop server.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= Handlers =================

    static class IndexHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange ex) throws IOException {
            if (!ex.getRequestURI().getPath().equals("/")) {
                sendResponse(ex, 404, "Page Not Found", "text/plain");
                return;
            }
            String content = readFile(VIEWS_DIR + "index.jsp");
            sendResponse(ex, 200, content, "text/html; charset=UTF-8");
        }
    }

    static class LoginServletHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange ex) throws IOException {
            if ("POST".equalsIgnoreCase(ex.getRequestMethod())) {
                Map<String, String> params = parseFormData(ex);
                String badgeId = params.getOrDefault("badgeId", "Officer");
                String sessionId = UUID.randomUUID().toString();
                AuthFilter.createSession(sessionId, badgeId, "Sub-Inspector / IO");

                ex.getResponseHeaders().set("Set-Cookie", "SESSIONID=" + sessionId + "; Path=/; HttpOnly");
                ex.getResponseHeaders().set("Location", "/dashboard");
                ex.sendResponseHeaders(302, -1);
            } else {
                ex.getResponseHeaders().set("Location", "/");
                ex.sendResponseHeaders(302, -1);
            }
        }
    }

    static class DashboardHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange ex) throws IOException {
            String sessionId = getCookie(ex, "SESSIONID");
            String username = AuthFilter.getSessionUser(sessionId);
            String role = AuthFilter.getSessionRole(sessionId);

            List<Criminal> criminals = DatabaseManager.getInstance().getAllCriminals();
            List<FIR> firs = DatabaseManager.getInstance().getAllFIRs();

            long inCustodyCount = criminals.stream()
                    .filter(c -> !c.isCurrentlyReleased())
                    .count();

            long activeFirsCount = firs.stream()
                    .filter(f -> !"Closed".equalsIgnoreCase(f.getStatus()))
                    .count();

            long closedFirsCount = firs.stream()
                    .filter(f -> "Closed".equalsIgnoreCase(f.getStatus()) || "Chargesheeted".equalsIgnoreCase(f.getStatus()))
                    .count();

            // Build FIR table rows
            StringBuilder firRows = new StringBuilder();
            for (int i = 0; i < Math.min(5, firs.size()); i++) {
                FIR f = firs.get(i);
                String badgeClass = "badge-investigation";
                if ("Closed".equalsIgnoreCase(f.getStatus())) badgeClass = "badge-released";
                else if ("Chargesheeted".equalsIgnoreCase(f.getStatus())) badgeClass = "badge-chargesheet";

                firRows.append("<tr>")
                        .append("<td><strong>").append(f.getFirId()).append("</strong></td>")
                        .append("<td>").append(f.getComplainantName()).append("</td>")
                        .append("<td>").append(f.getCrimeCategory()).append("</td>")
                        .append("<td><code style='color:#ffd43b;'>").append(f.getIpcSection()).append("</code></td>")
                        .append("<td>").append(f.getInvestigatingOfficer()).append("</td>")
                        .append("<td><span class='badge ").append(badgeClass).append("'>").append(f.getStatus()).append("</span></td>")
                        .append("</tr>");
            }

            // Build Criminal table rows
            StringBuilder crimRows = new StringBuilder();
            for (int i = 0; i < Math.min(5, criminals.size()); i++) {
                Criminal c = criminals.get(i);
                CrimeRecord latest = c.getLatestCrime();
                String sec = latest != null ? latest.getImplementedSection() : "N/A";
                String loc = latest != null ? latest.getCrimeLocation() : "Rajkot";
                String badge = c.isCurrentlyReleased()
                        ? "<span class='badge badge-released'>RELEASED</span>"
                        : "<span class='badge badge-custody'>IN CUSTODY</span>";

                crimRows.append("<tr>")
                        .append("<td><code style='color:#ffc107;'>").append(c.getCriminalId()).append("</code></td>")
                        .append("<td><strong>").append(c.getName()).append("</strong></td>")
                        .append("<td>").append(c.getAge()).append("</td>")
                        .append("<td>").append(c.getHeight()).append("</td>")
                        .append("<td>").append(sec).append("</td>")
                        .append("<td>").append(loc).append("</td>")
                        .append("<td>").append(badge).append("</td>")
                        .append("<td><a href='/criminal-detail?id=").append(c.getCriminalId()).append("' style='color:#ffc107; font-weight:bold; text-decoration:none;'>Dossier &rarr;</a></td>")
                        .append("</tr>");
            }

            String template = readFile(VIEWS_DIR + "dashboard.jsp");
            String html = template
                    .replace("{{LOGGED_IN_USER}}", username)
                    .replace("{{LOGGED_IN_ROLE}}", role)
                    .replace("{{TOTAL_CRIMINALS}}", String.valueOf(criminals.size()))
                    .replace("{{IN_CUSTODY_COUNT}}", String.valueOf(inCustodyCount))
                    .replace("{{ACTIVE_FIRS}}", String.valueOf(activeFirsCount))
                    .replace("{{CLOSED_FIRS}}", String.valueOf(closedFirsCount))
                    .replace("{{RECENT_FIRS_ROWS}}", firRows.toString())
                    .replace("{{RECENT_CRIMINALS_ROWS}}", crimRows.toString());

            sendResponse(ex, 200, html, "text/html; charset=UTF-8");
        }
    }

    static class CriminalsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange ex) throws IOException {
            String query = getQueryParam(ex.getRequestURI().getQuery(), "q");
            List<Criminal> list = DatabaseManager.getInstance().searchCriminals(query, query);

            StringBuilder cards = new StringBuilder();
            for (Criminal c : list) {
                CrimeRecord latest = c.getLatestCrime();
                String sec = latest != null ? latest.getImplementedSection() : "N/A";
                String reason = latest != null ? latest.getReasonForArrest() : "Under Police Surveillance";
                String badge = c.isCurrentlyReleased()
                        ? "<span class='badge badge-released'>RELEASED</span>"
                        : "<span class='badge badge-custody'>IN CUSTODY</span>";

                cards.append("<div class='criminal-card'>")
                        .append("  <div class='card-top'>")
                        .append("    <img src='/images/front' class='mugshot-thumb' alt='Mugshot'>")
                        .append("    <div class='criminal-meta'>")
                        .append("      <div class='criminal-name'>").append(c.getName()).append("</div>")
                        .append("      <div class='criminal-id'>").append(c.getCriminalId()).append("</div>")
                        .append("      <div class='criminal-info'>Age: ").append(c.getAge()).append(" &bull; Height: ").append(c.getHeight()).append("</div>")
                        .append("      <div style='margin-top:6px;'>").append(badge).append("</div>")
                        .append("    </div>")
                        .append("  </div>")
                        .append("  <div class='card-body'>")
                        .append("    <div class='crime-tag'><strong>Charge:</strong> ").append(reason).append("</div>")
                        .append("    <div style='font-size:12px; color:#8d99ae;'><strong>IPC:</strong> ").append(sec).append("</div>")
                        .append("  </div>")
                        .append("  <div class='card-footer'>")
                        .append("    <span style='font-size:11px; color:#8d99ae;'>Archive Verified</span>")
                        .append("    <a href='/criminal-detail?id=").append(c.getCriminalId()).append("' class='btn-view'>View Full Dossier &rarr;</a>")
                        .append("  </div>")
                        .append("</div>");
            }

            String template = readFile(VIEWS_DIR + "criminals.jsp");
            String html = template
                    .replace("{{SEARCH_QUERY}}", query)
                    .replace("{{CRIMINAL_CARDS}}", cards.toString());

            sendResponse(ex, 200, html, "text/html; charset=UTF-8");
        }
    }

    static class CriminalDetailHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange ex) throws IOException {
            String id = getQueryParam(ex.getRequestURI().getQuery(), "id");
            if (id.isEmpty()) id = "RCB-2024-089";

            Criminal c = DatabaseManager.getInstance().getCriminalById(id);
            if (c == null) {
                sendResponse(ex, 404, "Criminal record not found", "text/plain");
                return;
            }

            String badge = c.isCurrentlyReleased()
                    ? "<span class='badge badge-released' style='font-size:13px; padding:6px 14px;'>RELEASED FROM DETENTION</span>"
                    : "<span class='badge badge-custody' style='font-size:13px; padding:6px 14px;'>IN JUDICIAL CUSTODY</span>";

            StringBuilder crimesHtml = new StringBuilder();
            List<CrimeRecord> records = c.getCrimeRecords();
            for (CrimeRecord r : records) {
                String rBadge = r.isReleased()
                        ? "<span class='badge badge-released'>Released</span>"
                        : "<span class='badge badge-custody'>In Custody</span>";

                crimesHtml.append("<div class='crime-record-card'>")
                        .append("  <div class='crime-title'>")
                        .append("    <h4>").append(r.getReasonForArrest()).append(" (Case ID: ").append(r.getRecordId()).append(")</h4>")
                        .append("    <div>").append(rBadge).append("</div>")
                        .append("  </div>")
                        .append("  <div style='font-size:13px; color:#ced4da; margin-bottom:8px;'><strong>IPC Sections:</strong> <span style='color:#ffd43b;'>").append(r.getImplementedSection()).append("</span></div>")
                        .append("  <div style='font-size:12px; color:#8d99ae; display:grid; grid-template-columns: repeat(3, 1fr); gap:10px;'>")
                        .append("    <div><strong>Location:</strong> ").append(r.getCrimeLocation()).append("</div>")
                        .append("    <div><strong>Gang/Syndicate:</strong> ").append(r.getGangName()).append("</div>")
                        .append("    <div><strong>Partners:</strong> ").append(r.getPartnerNames()).append("</div>")
                        .append("    <div><strong>Sentence:</strong> ").append(r.getImprisonmentFrom()).append(" to ").append(r.getImprisonmentTo()).append("</div>")
                        .append("    <div><strong>Officer in Charge:</strong> ").append(r.getOfficerRank()).append(" ").append(r.getOfficerName()).append("</div>")
                        .append("  </div>")
                        .append("</div>");
            }

            String template = readFile(VIEWS_DIR + "criminal-detail.jsp");
            String html = template
                    .replace("{{CRIMINAL_NAME}}", c.getName())
                    .replace("{{CRIMINAL_ID}}", c.getCriminalId())
                    .replace("{{CRIMINAL_AGE}}", c.getAge())
                    .replace("{{CRIMINAL_HEIGHT}}", c.getHeight())
                    .replace("{{CRIMINAL_ADDRESS}}", c.getAddress())
                    .replace("{{STATUS_BADGE}}", badge)
                    .replace("{{CUSTODY_STATUS_TEXT}}", c.getCurrentCustodyStatus())
                    .replace("{{CRIME_RECORDS_HTML}}", crimesHtml.toString());

            sendResponse(ex, 200, html, "text/html; charset=UTF-8");
        }
    }

    static class FIRHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange ex) throws IOException {
            if ("POST".equalsIgnoreCase(ex.getRequestMethod())) {
                Map<String, String> params = parseFormData(ex);
                String nextId = DatabaseManager.getInstance().generateNextFIRId();

                FIR fir = new FIR(
                        nextId,
                        params.getOrDefault("complainantName", "Citizen"),
                        params.getOrDefault("complainantPhone", ""),
                        params.getOrDefault("complainantAddress", "Rajkot"),
                        params.getOrDefault("crimeCategory", "General Offense"),
                        params.getOrDefault("ipcSection", "IPC 379"),
                        params.getOrDefault("incidentDate", "Today"),
                        params.getOrDefault("incidentLocation", "Rajkot"),
                        params.getOrDefault("suspectDetails", "Under investigation"),
                        params.getOrDefault("description", "Complaint lodged"),
                        "Rajkot Crime Branch HQ",
                        params.getOrDefault("investigatingOfficer", "PI M. S. Gohil"),
                        "Under Investigation"
                );

                DatabaseManager.getInstance().saveFIR(fir);
                ex.getResponseHeaders().set("Location", "/firs");
                ex.sendResponseHeaders(302, -1);
                return;
            }

            // GET Request -> List FIRs
            List<FIR> firs = DatabaseManager.getInstance().getAllFIRs();
            StringBuilder rows = new StringBuilder();
            for (FIR f : firs) {
                String badgeClass = "badge-investigation";
                if ("Closed".equalsIgnoreCase(f.getStatus())) badgeClass = "badge-closed";
                else if ("Chargesheeted".equalsIgnoreCase(f.getStatus())) badgeClass = "badge-chargesheet";

                rows.append("<tr>")
                        .append("<td><strong style='color:#ffc107;'>").append(f.getFirId()).append("</strong></td>")
                        .append("<td>").append(f.getComplainantName()).append("</td>")
                        .append("<td>").append(f.getComplainantPhone()).append("</td>")
                        .append("<td>").append(f.getCrimeCategory()).append("</td>")
                        .append("<td><code style='color:#ffd43b;'>").append(f.getIpcSection()).append("</code></td>")
                        .append("<td>").append(f.getIncidentLocation()).append("</td>")
                        .append("<td>").append(f.getInvestigatingOfficer()).append("</td>")
                        .append("<td><span class='badge ").append(badgeClass).append("'>").append(f.getStatus()).append("</span></td>")
                        .append("<td>").append(f.getRegistrationDate()).append("</td>")
                        .append("</tr>");
            }

            String template = readFile(VIEWS_DIR + "fir-list.jsp");
            String html = template.replace("{{FIR_ROWS}}", rows.toString());
            sendResponse(ex, 200, html, "text/html; charset=UTF-8");
        }
    }

    static class FIRRegisterHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange ex) throws IOException {
            String content = readFile(VIEWS_DIR + "fir-register.jsp");
            sendResponse(ex, 200, content, "text/html; charset=UTF-8");
        }
    }

    static class LogoutHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange ex) throws IOException {
            String sessionId = getCookie(ex, "SESSIONID");
            AuthFilter.invalidateSession(sessionId);
            ex.getResponseHeaders().set("Set-Cookie", "SESSIONID=; Path=/; Max-Age=0");
            ex.getResponseHeaders().set("Location", "/");
            ex.sendResponseHeaders(302, -1);
        }
    }

    static class ImageHandler implements HttpHandler {
        private final String filename;
        ImageHandler(String filename) { this.filename = filename; }

        @Override
        public void handle(HttpExchange ex) throws IOException {
            File f = new File(filename);
            if (!f.exists()) {
                sendResponse(ex, 404, "Image not found", "text/plain");
                return;
            }
            byte[] bytes = Files.readAllBytes(f.toPath());
            ex.getResponseHeaders().set("Content-Type", "image/png");
            ex.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = ex.getResponseBody()) {
                os.write(bytes);
            }
        }
    }

    // ================= Spring REST API Handlers =================

    static class RestCriminalsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange ex) throws IOException {
            List<Criminal> criminals = DatabaseManager.getInstance().getAllCriminals();
            StringBuilder sb = new StringBuilder();
            sb.append("[\n");
            for (int i = 0; i < criminals.size(); i++) {
                Criminal c = criminals.get(i);
                sb.append("  {\n")
                        .append("    \"criminalId\": \"").append(c.getCriminalId()).append("\",\n")
                        .append("    \"name\": \"").append(c.getName()).append("\",\n")
                        .append("    \"age\": \"").append(c.getAge()).append("\",\n")
                        .append("    \"height\": \"").append(c.getHeight()).append("\",\n")
                        .append("    \"address\": \"").append(c.getAddress()).append("\",\n")
                        .append("    \"custodyStatus\": \"").append(c.getCurrentCustodyStatus()).append("\",\n")
                        .append("    \"crimeCount\": ").append(c.getCrimeRecords().size()).append("\n")
                        .append("  }").append(i < criminals.size() - 1 ? "," : "").append("\n");
            }
            sb.append("]\n");

            sendResponse(ex, 200, sb.toString(), "application/json; charset=UTF-8");
        }
    }

    static class RestFIRsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange ex) throws IOException {
            List<FIR> firs = DatabaseManager.getInstance().getAllFIRs();
            StringBuilder sb = new StringBuilder();
            sb.append("[\n");
            for (int i = 0; i < firs.size(); i++) {
                FIR f = firs.get(i);
                sb.append("  {\n")
                        .append("    \"firId\": \"").append(f.getFirId()).append("\",\n")
                        .append("    \"complainant\": \"").append(f.getComplainantName()).append("\",\n")
                        .append("    \"category\": \"").append(f.getCrimeCategory()).append("\",\n")
                        .append("    \"ipc\": \"").append(f.getIpcSection()).append("\",\n")
                        .append("    \"officer\": \"").append(f.getInvestigatingOfficer()).append("\",\n")
                        .append("    \"status\": \"").append(f.getStatus()).append("\",\n")
                        .append("    \"filedDate\": \"").append(f.getRegistrationDate()).append("\"\n")
                        .append("  }").append(i < firs.size() - 1 ? "," : "").append("\n");
            }
            sb.append("]\n");

            sendResponse(ex, 200, sb.toString(), "application/json; charset=UTF-8");
        }
    }

    static class RestAnalyticsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange ex) throws IOException {
            List<Criminal> criminals = DatabaseManager.getInstance().getAllCriminals();
            List<FIR> firs = DatabaseManager.getInstance().getAllFIRs();

            long inCustody = criminals.stream().filter(c -> !c.isCurrentlyReleased()).count();
            long released = criminals.stream().filter(Criminal::isCurrentlyReleased).count();
            long pendingFIRs = firs.stream().filter(f -> "Under Investigation".equalsIgnoreCase(f.getStatus())).count();
            long closedFIRs = firs.stream().filter(f -> "Closed".equalsIgnoreCase(f.getStatus())).count();

            String json = "{\n" +
                    "  \"status\": \"SUCCESS\",\n" +
                    "  \"jurisdiction\": \"Rajkot Crime Branch\",\n" +
                    "  \"ajtModule\": \"Spring REST Controller + Spring Boot\",\n" +
                    "  \"analytics\": {\n" +
                    "    \"totalCriminals\": " + criminals.size() + ",\n" +
                    "    \"inJudicialCustody\": " + inCustody + ",\n" +
                    "    \"releasedOffenders\": " + released + ",\n" +
                    "    \"totalFIRs\": " + firs.size() + ",\n" +
                    "    \"firsUnderInvestigation\": " + pendingFIRs + ",\n" +
                    "    \"firsClosed\": " + closedFIRs + "\n" +
                    "  }\n" +
                    "}\n";

            sendResponse(ex, 200, json, "application/json; charset=UTF-8");
        }
    }

    // ================= Utility Helpers =================

    private static String readFile(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) return "<html><body><h1>View File Not Found: " + path + "</h1></body></html>";
            String raw = new String(Files.readAllBytes(f.toPath()), StandardCharsets.UTF_8);
            // Process JSP directives so browser renders clean HTML view
            return raw.replaceAll("(?s)<%@[^>]*%>\\s*", "");
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }

    private static void sendResponse(HttpExchange ex, int statusCode, String content, String contentType) throws IOException {
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().set("Content-Type", contentType);
        ex.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        ex.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = ex.getResponseBody()) {
            os.write(bytes);
        }
    }

    private static Map<String, String> parseFormData(HttpExchange ex) throws IOException {
        Map<String, String> map = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(ex.getRequestBody(), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String query = sb.toString();
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf('=');
                if (idx != -1) {
                    String k = URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8);
                    String v = URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8);
                    map.put(k, v);
                }
            }
        }
        return map;
    }

    private static String getQueryParam(String query, String param) {
        if (query == null || query.isEmpty()) return "";
        for (String pair : query.split("&")) {
            int idx = pair.indexOf('=');
            if (idx != -1 && pair.substring(0, idx).equalsIgnoreCase(param)) {
                return URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8);
            }
        }
        return "";
    }

    private static String getCookie(HttpExchange ex, String name) {
        List<String> cookies = ex.getRequestHeaders().get("Cookie");
        if (cookies != null) {
            for (String c : cookies) {
                for (String piece : c.split(";")) {
                    String[] parts = piece.trim().split("=");
                    if (parts.length == 2 && parts[0].equalsIgnoreCase(name)) {
                        return parts[1];
                    }
                }
            }
        }
        return "";
    }
}
