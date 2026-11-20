<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Criminal Dossiers - Rajkot Crime Branch</title>
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
        .nav-link { color: #ced4da; text-decoration: none; font-size: 13px; font-weight: 600; padding: 6px 12px; border-radius: 4px; transition: 0.2s; }
        .nav-link:hover, .nav-link.active { color: var(--gold); background: var(--navy-light); }
        .container { max-width: 1240px; margin: 30px auto; padding: 0 20px; flex: 1; }
        .header-bar { display: flex; justify-content: space-between; align-items: center; margin-bottom: 25px; flex-wrap: wrap; gap: 15px; }
        .header-bar h2 { font-size: 24px; color: #fff; }
        .header-bar h2 span { color: var(--gold); }
        .search-box { display: flex; gap: 10px; }
        .search-input { padding: 8px 14px; background: var(--navy-mid); border: 1px solid var(--border-color); border-radius: 6px; color: #fff; font-size: 13px; width: 250px; }
        .btn-search { padding: 8px 16px; background: var(--gold); color: var(--navy-dark); border: none; border-radius: 6px; font-weight: bold; cursor: pointer; }
        .criminals-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(360px, 1fr)); gap: 20px; }
        .criminal-card { background: var(--navy-mid); border: 1px solid var(--border-color); border-radius: 8px; overflow: hidden; display: flex; flex-direction: column; transition: transform 0.2s, border-color 0.2s; }
        .criminal-card:hover { transform: translateY(-4px); border-color: var(--gold); box-shadow: 0 8px 20px rgba(0,0,0,0.4); }
        .card-top { display: flex; padding: 15px; gap: 15px; border-bottom: 1px solid var(--border-color); }
        .mugshot-thumb { width: 90px; height: 110px; border-radius: 6px; object-fit: cover; border: 2px solid var(--border-color); background: var(--navy-dark); }
        .criminal-meta { flex: 1; }
        .criminal-name { font-size: 16px; font-weight: bold; color: #fff; margin-bottom: 4px; }
        .criminal-id { font-size: 12px; color: var(--gold); font-family: monospace; margin-bottom: 6px; }
        .criminal-info { font-size: 12px; color: var(--text-muted); line-height: 1.5; }
        .card-body { padding: 15px; flex: 1; font-size: 13px; }
        .crime-tag { background: var(--navy-dark); border: 1px solid var(--border-color); border-radius: 4px; padding: 6px 10px; margin-bottom: 8px; color: #ced4da; }
        .badge { display: inline-block; padding: 4px 10px; border-radius: 12px; font-size: 11px; font-weight: bold; }
        .badge-custody { background: rgba(220, 53, 69, 0.2); color: #ff6b6b; border: 1px solid #dc3545; }
        .badge-released { background: rgba(40, 167, 69, 0.2); color: #51cf66; border: 1px solid #28a745; }
        .card-footer { padding: 12px 15px; background: var(--navy-dark); border-top: 1px solid var(--border-color); display: flex; justify-content: space-between; align-items: center; }
        .btn-view { padding: 6px 14px; background: var(--navy-light); border: 1px solid var(--border-color); color: var(--gold); text-decoration: none; border-radius: 4px; font-size: 12px; font-weight: bold; }
        .btn-view:hover { background: var(--gold); color: var(--navy-dark); }
        .footer { background-color: var(--navy-mid); border-top: 1px solid var(--border-color); padding: 15px 30px; text-align: center; color: var(--text-muted); font-size: 12px; margin-top: auto; }
    </style>
</head>
<body>
    <div class="navbar">
        <div class="logo-box">
            <div class="badge-circle">RCB</div>
            <div class="logo-text">
                <h1>RAJKOT CRIME BRANCH</h1>
                <p>Criminal Intelligence &amp; Offender Profiles</p>
            </div>
        </div>
        <div class="nav-links">
            <a href="/dashboard" class="nav-link">Dashboard</a>
            <a href="/criminals" class="nav-link active">Criminal Dossiers</a>
            <a href="/firs" class="nav-link">FIR Repository</a>
            <a href="/fir-register" class="nav-link">File Online FIR</a>
            <a href="/logout" class="nav-link" style="color: #ff6b6b;">Logout</a>
        </div>
    </div>

    <div class="container">
        <div class="header-bar">
            <div>
                <h2>Central Criminal <span>Records Directory</span></h2>
                <p style="font-size: 13px; color: var(--text-muted); margin-top: 4px;">Registered Repeat Offenders, Gang Profiles &amp; Custody Monitoring</p>
            </div>
            <form action="/criminals" method="GET" class="search-box">
                <input type="text" name="q" class="search-input" placeholder="Search by name, ID or IPC..." value="{{SEARCH_QUERY}}">
                <button type="submit" class="btn-search">Search</button>
            </form>
        </div>

        <div class="criminals-grid">
            {{CRIMINAL_CARDS}}
        </div>
    </div>

    <div class="footer">
        &copy; 2026 Rajkot Crime Branch • AJT CSE Project (Servlet, JSP, Spring, Swing)
    </div>
</body>
</html>
