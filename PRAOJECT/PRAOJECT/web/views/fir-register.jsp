<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>File Online FIR - Rajkot Crime Branch</title>
    <style>
        :root {
            --navy-dark: #0d1b2a;
            --navy-mid: #1b263b;
            --navy-light: #25334d;
            --gold: #ffc107;
            --gold-hover: #e0a800;
            --text-light: #f8f9fa;
            --text-muted: #8d99ae;
            --border-color: #314666;
        }
        * { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }
        body { background-color: var(--navy-dark); color: var(--text-light); min-height: 100vh; display: flex; flex-direction: column; }
        .navbar { background-color: var(--navy-mid); border-bottom: 2px solid var(--gold); padding: 12px 30px; display: flex; justify-content: space-between; align-items: center; }
        .logo-box { display: flex; align-items: center; gap: 15px; }
        .badge-circle { width: 40px; height: 40px; border-radius: 50%; background: radial-gradient(circle, var(--gold) 40%, #b8860b); display: flex; align-items: center; justify-content: center; font-weight: bold; color: var(--navy-dark); border: 2px solid #fff; font-size: 13px; }
        .logo-text h1 { font-size: 16px; color: var(--gold); letter-spacing: 1px; }
        .nav-links { display: flex; gap: 15px; align-items: center; }
        .nav-link { color: #ced4da; text-decoration: none; font-size: 13px; font-weight: 600; padding: 6px 12px; border-radius: 4px; }
        .nav-link:hover, .nav-link.active { color: var(--gold); background: var(--navy-light); }
        .container { max-width: 900px; margin: 30px auto; padding: 0 20px; flex: 1; }
        .card { background: var(--navy-mid); border: 1px solid var(--border-color); border-radius: 10px; padding: 30px; box-shadow: 0 8px 24px rgba(0,0,0,0.3); }
        .card-header { border-bottom: 1px solid var(--border-color); padding-bottom: 15px; margin-bottom: 25px; }
        .card-header h2 { font-size: 22px; color: #fff; }
        .card-header h2 span { color: var(--gold); }
        .form-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 20px; }
        .form-group { margin-bottom: 16px; }
        .form-group.full { grid-column: span 2; }
        .form-group label { display: block; margin-bottom: 6px; font-weight: 600; font-size: 13px; color: #ced4da; }
        .form-control { width: 100%; padding: 10px 14px; background-color: var(--navy-dark); border: 1px solid var(--border-color); border-radius: 6px; color: #fff; font-size: 13px; }
        .form-control:focus { outline: none; border-color: var(--gold); }
        textarea.form-control { resize: vertical; min-height: 90px; }
        .btn-submit { padding: 12px 28px; background-color: var(--gold); color: var(--navy-dark); border: none; border-radius: 6px; font-weight: bold; font-size: 14px; cursor: pointer; transition: 0.2s; }
        .btn-submit:hover { background-color: var(--gold-hover); }
        .btn-cancel { padding: 12px 20px; background-color: transparent; border: 1px solid var(--border-color); color: #ced4da; border-radius: 6px; font-weight: bold; font-size: 14px; cursor: pointer; margin-right: 12px; text-decoration: none; }
        .footer { background-color: var(--navy-mid); border-top: 1px solid var(--border-color); padding: 15px 30px; text-align: center; color: var(--text-muted); font-size: 12px; margin-top: auto; }
    </style>
</head>
<body>
    <div class="navbar">
        <div class="logo-box">
            <div class="badge-circle">RCB</div>
            <div class="logo-text">
                <h1>RAJKOT CRIME BRANCH</h1>
                <p>Online First Information Report (FIR) Filing System</p>
            </div>
        </div>
        <div class="nav-links">
            <a href="/dashboard" class="nav-link">Dashboard</a>
            <a href="/criminals" class="nav-link">Criminal Dossiers</a>
            <a href="/firs" class="nav-link">FIR Repository</a>
            <a href="/fir-register" class="nav-link active">File Online FIR</a>
        </div>
    </div>

    <div class="container">
        <div class="card">
            <div class="card-header">
                <h2>Official <span>FIR Registration Portal</span></h2>
                <p style="font-size: 13px; color: var(--text-muted); margin-top: 5px;">File an official First Information Report with Rajkot Crime Branch</p>
            </div>

            <form action="/firs" method="POST">
                <div class="form-grid">
                    <div class="form-group">
                        <label>Complainant Full Name *</label>
                        <input type="text" name="complainantName" class="form-control" placeholder="e.g. Rajesh Kumar Mehta" required>
                    </div>

                    <div class="form-group">
                        <label>Contact Phone Number *</label>
                        <input type="text" name="complainantPhone" class="form-control" placeholder="e.g. +91 98250 12345" required>
                    </div>

                    <div class="form-group full">
                        <label>Permanent / Residential Address *</label>
                        <input type="text" name="complainantAddress" class="form-control" placeholder="House/Flat, Street, Area, City" required>
                    </div>

                    <div class="form-group">
                        <label>Crime Category *</label>
                        <select name="crimeCategory" class="form-control" required>
                            <option>Armed Robbery &amp; Dacoity</option>
                            <option>Theft &amp; Housebreak</option>
                            <option>Cyber Crime &amp; Online Fraud</option>
                            <option>Extortion &amp; Blackmail</option>
                            <option>Assault &amp; Physical Violence</option>
                            <option>Narcotics &amp; Smuggling</option>
                            <option>Homicide &amp; Attempt to Murder</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label>Applicable IPC / Special Act Section</label>
                        <input type="text" name="ipcSection" class="form-control" placeholder="e.g. IPC 379 / IPC 420" value="IPC 379">
                    </div>

                    <div class="form-group">
                        <label>Incident Date &amp; Time *</label>
                        <input type="text" name="incidentDate" class="form-control" placeholder="DD/MM/YYYY HH:MM" value="31/10/2026 09:30" required>
                    </div>

                    <div class="form-group">
                        <label>Incident Location *</label>
                        <input type="text" name="incidentLocation" class="form-control" placeholder="Location in Rajkot" required>
                    </div>

                    <div class="form-group full">
                        <label>Suspect(s) Details / Identification (if known)</label>
                        <input type="text" name="suspectDetails" class="form-control" placeholder="Physical description, vehicle number, known associates">
                    </div>

                    <div class="form-group full">
                        <label>Detailed Incident Description *</label>
                        <textarea name="description" class="form-control" placeholder="Provide complete facts of the crime chronologically..." required></textarea>
                    </div>

                    <div class="form-group">
                        <label>Assigned Investigating Officer</label>
                        <select name="investigatingOfficer" class="form-control">
                            <option>PI M. S. Gohil (Crime Branch)</option>
                            <option>PSI R. B. Zala (Cyber Unit)</option>
                            <option>SP Vikram Rathore (Special Cell)</option>
                            <option>DSP K. L. Parmar (Anti-Dacoity)</option>
                        </select>
                    </div>
                </div>

                <div style="margin-top: 25px; text-align: right;">
                    <a href="/firs" class="btn-cancel">Cancel</a>
                    <button type="submit" class="btn-submit">Submit &amp; Register FIR &rarr;</button>
                </div>
            </form>
        </div>
    </div>

    <div class="footer">
        &copy; 2026 Rajkot Crime Branch • AJT CSE Project (Servlet, JSP, Spring, Swing)
    </div>
</body>
</html>
