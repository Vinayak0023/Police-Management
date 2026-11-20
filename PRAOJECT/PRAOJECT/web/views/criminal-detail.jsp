<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Dossier: {{CRIMINAL_NAME}} - Rajkot Crime Branch</title>
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
            --danger: #dc3545;
            --success: #28a745;
        }
        * { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }
        body { background-color: var(--navy-dark); color: var(--text-light); min-height: 100vh; display: flex; flex-direction: column; }
        .navbar { background-color: var(--navy-mid); border-bottom: 2px solid var(--gold); padding: 12px 30px; display: flex; justify-content: space-between; align-items: center; }
        .logo-box { display: flex; align-items: center; gap: 15px; }
        .badge-circle { width: 40px; height: 40px; border-radius: 50%; background: radial-gradient(circle, var(--gold) 40%, #b8860b); display: flex; align-items: center; justify-content: center; font-weight: bold; color: var(--navy-dark); border: 2px solid #fff; font-size: 13px; }
        .logo-text h1 { font-size: 16px; color: var(--gold); letter-spacing: 1px; }
        .nav-links { display: flex; gap: 15px; align-items: center; }
        .nav-link { color: #ced4da; text-decoration: none; font-size: 13px; font-weight: 600; padding: 6px 12px; border-radius: 4px; }
        .nav-link:hover { color: var(--gold); }
        .container { max-width: 1200px; margin: 30px auto; padding: 0 20px; flex: 1; }
        .back-link { display: inline-block; margin-bottom: 15px; color: var(--gold); text-decoration: none; font-size: 13px; font-weight: bold; }
        .profile-card { background: var(--navy-mid); border: 1px solid var(--border-color); border-radius: 10px; overflow: hidden; margin-bottom: 30px; }
        .profile-header { background: linear-gradient(90deg, var(--navy-dark), var(--navy-mid)); padding: 25px; border-bottom: 1px solid var(--border-color); display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap; gap: 15px; }
        .header-info h2 { font-size: 26px; color: #fff; }
        .header-info span { color: var(--gold); font-family: monospace; font-size: 15px; }
        .mugshot-gallery { display: grid; grid-template-columns: repeat(3, 1fr); gap: 15px; padding: 25px; background: var(--navy-dark); border-bottom: 1px solid var(--border-color); }
        .mugshot-item { text-align: center; }
        .mugshot-img { width: 100%; height: 260px; object-fit: cover; border-radius: 8px; border: 2px solid var(--border-color); box-shadow: 0 4px 12px rgba(0,0,0,0.5); }
        .mugshot-label { margin-top: 8px; font-size: 12px; color: var(--gold); text-transform: uppercase; font-weight: bold; }
        .details-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 20px; padding: 25px; }
        .detail-item { background: var(--navy-dark); border: 1px solid var(--border-color); border-radius: 6px; padding: 15px; }
        .detail-label { font-size: 11px; text-transform: uppercase; color: var(--text-muted); font-weight: bold; }
        .detail-val { font-size: 15px; color: #fff; margin-top: 5px; font-weight: 500; }
        .crimes-section { padding: 25px; border-top: 1px solid var(--border-color); }
        .crimes-section h3 { font-size: 18px; color: var(--gold); margin-bottom: 15px; }
        .crime-record-card { background: var(--navy-dark); border: 1px solid var(--border-color); border-radius: 6px; padding: 18px; margin-bottom: 15px; }
        .crime-title { display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px; }
        .crime-title h4 { font-size: 15px; color: #fff; }
        .badge { display: inline-block; padding: 4px 10px; border-radius: 12px; font-size: 11px; font-weight: bold; }
        .badge-custody { background: rgba(220, 53, 69, 0.2); color: #ff6b6b; border: 1px solid #dc3545; }
        .badge-released { background: rgba(40, 167, 69, 0.2); color: #51cf66; border: 1px solid #28a745; }
        .footer { background-color: var(--navy-mid); border-top: 1px solid var(--border-color); padding: 15px 30px; text-align: center; color: var(--text-muted); font-size: 12px; margin-top: auto; }
    </style>
</head>
<body>
    <div class="navbar">
        <div class="logo-box">
            <div class="badge-circle">RCB</div>
            <div class="logo-text">
                <h1>RAJKOT CRIME BRANCH</h1>
                <p>Confidential Dossier &bull; Gujarat Police Central Archives</p>
            </div>
        </div>
        <div class="nav-links">
            <a href="/dashboard" class="nav-link">Dashboard</a>
            <a href="/criminals" class="nav-link">Criminal Dossiers</a>
            <a href="/firs" class="nav-link">FIR Repository</a>
        </div>
    </div>

    <div class="container">
        <a href="/criminals" class="back-link">&larr; Back to Criminal Directory</a>

        <div class="profile-card">
            <div class="profile-header">
                <div class="header-info">
                    <h2>{{CRIMINAL_NAME}}</h2>
                    <span>POLICE DOSSIER ID: {{CRIMINAL_ID}}</span>
                </div>
                <div>
                    {{STATUS_BADGE}}
                </div>
            </div>

            <!-- 3-Angle Mugshots -->
            <div class="mugshot-gallery">
                <div class="mugshot-item">
                    <img src="/images/left" class="mugshot-img" alt="Left Profile">
                    <div class="mugshot-label">Angle 1: Left Profile</div>
                </div>
                <div class="mugshot-item">
                    <img src="/images/front" class="mugshot-img" alt="Front Mugshot">
                    <div class="mugshot-label">Angle 2: Frontal Slate</div>
                </div>
                <div class="mugshot-item">
                    <img src="/images/right" class="mugshot-img" alt="Right Profile">
                    <div class="mugshot-label">Angle 3: Right Profile</div>
                </div>
            </div>

            <!-- Profile Details -->
            <div class="details-grid">
                <div class="detail-item">
                    <div class="detail-label">Age &amp; Physical Build</div>
                    <div class="detail-val">{{CRIMINAL_AGE}} Years | Height: {{CRIMINAL_HEIGHT}}</div>
                </div>
                <div class="detail-item">
                    <div class="detail-label">Current Judicial Status</div>
                    <div class="detail-val">{{CUSTODY_STATUS_TEXT}}</div>
                </div>
                <div class="detail-item" style="grid-column: span 2;">
                    <div class="detail-label">Last Known Residential Address</div>
                    <div class="detail-val">{{CRIMINAL_ADDRESS}}</div>
                </div>
            </div>

            <!-- Recorded Offenses -->
            <div class="crimes-section">
                <h3>Recorded Criminal History &amp; Court Sentences</h3>
                {{CRIME_RECORDS_HTML}}
            </div>
        </div>
    </div>

    <div class="footer">
        &copy; 2026 Rajkot Crime Branch • AJT CSE Project (Servlet, JSP, Spring, Swing)
    </div>
</body>
</html>
