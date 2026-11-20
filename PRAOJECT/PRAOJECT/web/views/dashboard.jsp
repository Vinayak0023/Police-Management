<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Dashboard - Rajkot Crime Branch Portal</title>
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
        .nav-link { color: #ced4da; text-decoration: none; font-size: 13px; font-weight: 600; padding: 6px 12px; border-radius: 4px; transition: 0.2s; }
        .nav-link:hover, .nav-link.active { color: var(--gold); background: var(--navy-light); }
        .btn-logout { background: transparent; border: 1px solid var(--danger); color: #ff6b6b; padding: 6px 14px; border-radius: 4px; cursor: pointer; text-decoration: none; font-size: 13px; font-weight: bold; }
        .btn-logout:hover { background: var(--danger); color: #fff; }
        .container { max-width: 1240px; margin: 30px auto; padding: 0 20px; flex: 1; }
        .page-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 25px; }
        .page-header h2 { font-size: 24px; color: #fff; }
        .page-header h2 span { color: var(--gold); }
        .stats-row { display: grid; grid-template-columns: repeat(4, 1fr); gap: 20px; margin-bottom: 30px; }
        .stat-card { background: var(--navy-mid); border: 1px solid var(--border-color); border-radius: 8px; padding: 20px; display: flex; flex-direction: column; }
        .stat-card .title { font-size: 12px; color: var(--text-muted); text-transform: uppercase; font-weight: bold; }
        .stat-card .val { font-size: 32px; font-weight: bold; color: #fff; margin: 8px 0; }
        .stat-card .sub { font-size: 12px; color: var(--gold); }
        .grid-2-1 { display: grid; grid-template-columns: 2fr 1fr; gap: 25px; margin-bottom: 30px; }
        .panel { background: var(--navy-mid); border: 1px solid var(--border-color); border-radius: 8px; padding: 22px; }
        .panel-header { display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid var(--border-color); padding-bottom: 12px; margin-bottom: 15px; }
        .panel-header h3 { font-size: 16px; color: var(--gold); }
        .table-responsive { overflow-x: auto; }
        table { width: 100%; border-collapse: collapse; font-size: 13px; }
        th { background: var(--navy-dark); color: var(--gold); padding: 10px 12px; text-align: left; border-bottom: 2px solid var(--border-color); }
        td { padding: 12px; border-bottom: 1px solid var(--border-color); color: #e9ecef; }
        tr:hover { background: var(--navy-light); }
        .badge { display: inline-block; padding: 4px 10px; border-radius: 12px; font-size: 11px; font-weight: bold; }
        .badge-custody { background: rgba(220, 53, 69, 0.2); color: #ff6b6b; border: 1px solid #dc3545; }
        .badge-released { background: rgba(40, 167, 69, 0.2); color: #51cf66; border: 1px solid #28a745; }
        .badge-investigation { background: rgba(255, 193, 7, 0.2); color: #ffd43b; border: 1px solid #ffc107; }
        .badge-chargesheet { background: rgba(23, 162, 184, 0.2); color: #66d9e8; border: 1px solid #17a2b8; }
        .quick-actions { display: flex; flex-direction: column; gap: 12px; }
        .btn-action { display: block; text-align: center; text-decoration: none; padding: 12px; border-radius: 6px; font-weight: bold; font-size: 13px; transition: 0.2s; }
        .btn-action-gold { background: var(--gold); color: var(--navy-dark); }
        .btn-action-gold:hover { background: var(--gold-hover); }
        .btn-action-outline { border: 1px solid var(--border-color); background: var(--navy-dark); color: #ced4da; }
        .btn-action-outline:hover { border-color: var(--gold); color: var(--gold); }
        .footer { background-color: var(--navy-mid); border-top: 1px solid var(--border-color); padding: 15px 30px; text-align: center; color: var(--text-muted); font-size: 12px; margin-top: auto; }
    </style>
</head>
<body>
    <div class="navbar">
        <div class="logo-box">
            <div class="badge-circle">RCB</div>
            <div class="logo-text">
                <h1>RAJKOT CRIME BRANCH</h1>
                <p>Officer Command Dashboard • Gujarat Police</p>
            </div>
        </div>
        <div class="nav-links">
            <a href="/dashboard" class="nav-link active">Dashboard</a>
            <a href="/criminals" class="nav-link">Criminal Dossiers</a>
            <a href="/firs" class="nav-link">FIR Repository</a>
            <a href="/fir-register" class="nav-link">File Online FIR</a>
            <a href="/api/criminals" target="_blank" class="nav-link" style="color: #66d9e8;">⚡ Spring REST API</a>
            <a href="/logout" class="btn-logout">Logout</a>
        </div>
    </div>

    <div class="container">
        <div class="page-header">
            <h2>Command Center &bull; <span>Active Operations</span></h2>
            <div style="font-size: 13px; color: var(--text-muted);">
                Logged in as: <strong style="color: var(--gold);">{{LOGGED_IN_USER}}</strong> ({{LOGGED_IN_ROLE}})
            </div>
        </div>

        <!-- 4 Key Stat Cards -->
        <div class="stats-row">
            <div class="stat-card">
                <span class="title">Total Criminal Profiles</span>
                <span class="val">{{TOTAL_CRIMINALS}}</span>
                <span class="sub">&bull; Verified in Central DB</span>
            </div>
            <div class="stat-card">
                <span class="title">Suspects In Custody</span>
                <span class="val" style="color: #ff6b6b;">{{IN_CUSTODY_COUNT}}</span>
                <span class="sub">&bull; High Security Detention</span>
            </div>
            <div class="stat-card">
                <span class="title">Active FIRs Under Probe</span>
                <span class="val" style="color: #ffd43b;">{{ACTIVE_FIRS}}</span>
                <span class="sub">&bull; Investigation Underway</span>
            </div>
            <div class="stat-card">
                <span class="title">Disposed / Chargesheeted</span>
                <span class="val" style="color: #51cf66;">{{CLOSED_FIRS}}</span>
                <span class="sub">&bull; Transferred to Court</span>
            </div>
        </div>

        <div class="grid-2-1">
            <!-- Active FIRs Table Panel -->
            <div class="panel">
                <div class="panel-header">
                    <h3>Recent First Information Reports (FIRs)</h3>
                    <a href="/firs" style="font-size: 12px; color: var(--gold); text-decoration: none;">View All &rarr;</a>
                </div>
                <div class="table-responsive">
                    <table>
                        <thead>
                            <tr>
                                <th>FIR No.</th>
                                <th>Complainant</th>
                                <th>Crime Category</th>
                                <th>IPC Section</th>
                                <th>Investigating Officer</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            {{RECENT_FIRS_ROWS}}
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Quick Actions Panel -->
            <div class="panel">
                <div class="panel-header">
                    <h3>Quick Operations</h3>
                </div>
                <div class="quick-actions">
                    <a href="/fir-register" class="btn-action btn-action-gold">+ File New Online FIR</a>
                    <a href="/criminals" class="btn-action btn-action-outline">🔍 Search Criminal Database</a>
                    <a href="/firs" class="btn-action btn-action-outline">📑 Case Investigation Tracker</a>
                    <a href="/api/analytics/summary" target="_blank" class="btn-action btn-action-outline">📊 View Spring Analytics API</a>
                    <a href="/api/firs" target="_blank" class="btn-action btn-action-outline">⚡ Export JSON Feed</a>
                </div>
            </div>
        </div>

        <!-- Recent Criminal Dossiers Table Panel -->
        <div class="panel">
            <div class="panel-header">
                <h3>Tracked Repeat Offenders &amp; Criminal Records</h3>
                <a href="/criminals" style="font-size: 12px; color: var(--gold); text-decoration: none;">Full Directory &rarr;</a>
            </div>
            <div class="table-responsive">
                <table>
                    <thead>
                        <tr>
                            <th>Criminal ID</th>
                            <th>Offender Name</th>
                            <th>Age</th>
                            <th>Height</th>
                            <th>Last Known IPC Charge</th>
                            <th>Crime Location</th>
                            <th>Custody Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {{RECENT_CRIMINALS_ROWS}}
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div class="footer">
        &copy; 2026 Rajkot Crime Branch • AJT CSE Project (Servlet, JSP, Spring, Swing) • Academic Year 2026
    </div>
</body>
</html>
