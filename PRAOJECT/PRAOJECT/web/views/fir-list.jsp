<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>FIR Repository - Rajkot Crime Branch</title>
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
            --warning: #ffc107;
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
        .nav-link:hover, .nav-link.active { color: var(--gold); background: var(--navy-light); }
        .container { max-width: 1240px; margin: 30px auto; padding: 0 20px; flex: 1; }
        .header-bar { display: flex; justify-content: space-between; align-items: center; margin-bottom: 25px; flex-wrap: wrap; gap: 15px; }
        .header-bar h2 { font-size: 24px; color: #fff; }
        .header-bar h2 span { color: var(--gold); }
        .btn-gold { padding: 9px 18px; background: var(--gold); color: var(--navy-dark); border: none; border-radius: 6px; font-weight: bold; text-decoration: none; font-size: 13px; cursor: pointer; }
        .btn-gold:hover { background: var(--gold-hover); }
        .panel { background: var(--navy-mid); border: 1px solid var(--border-color); border-radius: 8px; padding: 22px; }
        .table-responsive { overflow-x: auto; }
        table { width: 100%; border-collapse: collapse; font-size: 13px; }
        th { background: var(--navy-dark); color: var(--gold); padding: 12px; text-align: left; border-bottom: 2px solid var(--border-color); }
        td { padding: 12px; border-bottom: 1px solid var(--border-color); color: #e9ecef; }
        tr:hover { background: var(--navy-light); }
        .badge { display: inline-block; padding: 4px 10px; border-radius: 12px; font-size: 11px; font-weight: bold; }
        .badge-investigation { background: rgba(255, 193, 7, 0.2); color: #ffd43b; border: 1px solid #ffc107; }
        .badge-chargesheet { background: rgba(23, 162, 184, 0.2); color: #66d9e8; border: 1px solid #17a2b8; }
        .badge-closed { background: rgba(40, 167, 69, 0.2); color: #51cf66; border: 1px solid #28a745; }
        .footer { background-color: var(--navy-mid); border-top: 1px solid var(--border-color); padding: 15px 30px; text-align: center; color: var(--text-muted); font-size: 12px; margin-top: auto; }
    </style>
</head>
<body>
    <div class="navbar">
        <div class="logo-box">
            <div class="badge-circle">RCB</div>
            <div class="logo-text">
                <h1>RAJKOT CRIME BRANCH</h1>
                <p>First Information Report (FIR) Legal Central Registry</p>
            </div>
        </div>
        <div class="nav-links">
            <a href="/dashboard" class="nav-link">Dashboard</a>
            <a href="/criminals" class="nav-link">Criminal Dossiers</a>
            <a href="/firs" class="nav-link active">FIR Repository</a>
            <a href="/fir-register" class="nav-link">File Online FIR</a>
        </div>
    </div>

    <div class="container">
        <div class="header-bar">
            <div>
                <h2>First Information Report <span>(FIR) Registry</span></h2>
                <p style="font-size: 13px; color: var(--text-muted); margin-top: 4px;">Formal Criminal Complaints, Investigation Milestones &amp; Chargesheet Status</p>
            </div>
            <a href="/fir-register" class="btn-gold">+ Register New FIR</a>
        </div>

        <div class="panel">
            <div class="table-responsive">
                <table>
                    <thead>
                        <tr>
                            <th>FIR Number</th>
                            <th>Complainant Name</th>
                            <th>Contact Phone</th>
                            <th>Crime Category</th>
                            <th>IPC Section</th>
                            <th>Incident Location</th>
                            <th>Investigating Officer</th>
                            <th>Status</th>
                            <th>Date Filed</th>
                        </tr>
                    </thead>
                    <tbody>
                        {{FIR_ROWS}}
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div class="footer">
        &copy; 2026 Rajkot Crime Branch • AJT CSE Project (Servlet, JSP, Spring, Swing)
    </div>
</body>
</html>
