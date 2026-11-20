<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Rajkot Crime Branch - Official Crime Management Portal</title>
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
        .navbar { background-color: var(--navy-mid); border-bottom: 2px solid var(--gold); padding: 15px 30px; display: flex; justify-content: space-between; align-items: center; }
        .logo-box { display: flex; align-items: center; gap: 15px; }
        .badge-circle { width: 45px; height: 45px; border-radius: 50%; background: radial-gradient(circle, var(--gold) 40%, #b8860b); display: flex; align-items: center; justify-content: center; font-weight: bold; color: var(--navy-dark); border: 2px solid #fff; font-size: 14px; }
        .logo-text h1 { font-size: 18px; color: var(--gold); letter-spacing: 1px; }
        .logo-text p { font-size: 11px; color: var(--text-muted); text-transform: uppercase; }
        .container { max-width: 1200px; margin: 40px auto; padding: 0 20px; flex: 1; }
        .hero { text-align: center; margin-bottom: 40px; padding: 40px 20px; background: linear-gradient(180deg, var(--navy-mid) 0%, rgba(27,38,59,0.4) 100%); border-radius: 12px; border: 1px solid var(--border-color); }
        .hero h2 { font-size: 32px; color: #fff; margin-bottom: 12px; }
        .hero h2 span { color: var(--gold); }
        .hero p { color: var(--text-muted); font-size: 15px; max-width: 700px; margin: 0 auto; }
        .grid-2 { display: grid; grid-template-columns: 1fr 1fr; gap: 30px; align-items: start; }
        .card { background-color: var(--navy-mid); border: 1px solid var(--border-color); border-radius: 10px; padding: 30px; box-shadow: 0 8px 24px rgba(0,0,0,0.3); }
        .card-header { border-bottom: 1px solid var(--border-color); padding-bottom: 15px; margin-bottom: 20px; display: flex; justify-content: space-between; align-items: center; }
        .card-header h3 { color: var(--gold); font-size: 20px; }
        .form-group { margin-bottom: 18px; }
        .form-group label { display: block; margin-bottom: 6px; font-weight: 600; font-size: 13px; color: #ced4da; }
        .form-control { width: 100%; padding: 12px; background-color: var(--navy-dark); border: 1px solid var(--border-color); border-radius: 6px; color: #fff; font-size: 14px; }
        .form-control:focus { outline: none; border-color: var(--gold); }
        .btn-gold { width: 100%; padding: 12px; background-color: var(--gold); color: var(--navy-dark); border: none; border-radius: 6px; font-weight: bold; font-size: 15px; cursor: pointer; transition: 0.2s; }
        .btn-gold:hover { background-color: var(--gold-hover); }
        .features-list { list-style: none; }
        .features-list li { margin-bottom: 15px; display: flex; gap: 12px; font-size: 14px; color: #ced4da; }
        .features-list li span { color: var(--gold); font-weight: bold; }
        .stat-grid { display: grid; grid-template-columns: repeat(3, 1fr); gap: 15px; margin-top: 25px; }
        .stat-item { background: var(--navy-dark); border: 1px solid var(--border-color); border-radius: 8px; padding: 15px; text-align: center; }
        .stat-num { font-size: 26px; font-weight: bold; color: var(--gold); }
        .stat-lbl { font-size: 11px; color: var(--text-muted); text-transform: uppercase; margin-top: 4px; }
        .footer { background-color: var(--navy-mid); border-top: 1px solid var(--border-color); padding: 15px 30px; text-align: center; color: var(--text-muted); font-size: 12px; margin-top: auto; }
    </style>
</head>
<body>
    <div class="navbar">
        <div class="logo-box">
            <div class="badge-circle">RCB</div>
            <div class="logo-text">
                <h1>RAJKOT CRIME BRANCH</h1>
                <p>Gujarat Police • Crime Records &amp; FIR Management System</p>
            </div>
        </div>
        <div>
            <span style="color: var(--gold); font-size: 12px; font-weight: bold; border: 1px solid var(--border-color); padding: 6px 14px; border-radius: 20px;">
                AJT CSE PROJECT • 01CE0308
            </span>
        </div>
    </div>

    <div class="container">
        <div class="hero">
            <h2>Welcome to <span>Crime Management Intelligence Portal</span></h2>
            <p>Unified enterprise law enforcement portal integrating Java Swing, Servlets, JavaServer Pages (JSP), and Spring MVC for centralized crime records, FIR logging, and offender profiling.</p>
        </div>

        <div class="grid-2">
            <!-- Officer Login Card -->
            <div class="card">
                <div class="card-header">
                    <h3>Officer Secure Login</h3>
                    <span style="font-size: 12px; color: var(--text-muted);">Servlet Authentication</span>
                </div>
                <form action="/login" method="POST">
                    <div class="form-group">
                        <label>Officer Badge / ID</label>
                        <input type="text" name="badgeId" class="form-control" value="RCB-OFFICER-01" required>
                    </div>
                    <div class="form-group">
                        <label>Password</label>
                        <input type="password" name="password" class="form-control" value="admin123" required>
                    </div>
                    <div class="form-group">
                        <label>Assigned Unit / Branch</label>
                        <select name="unit" class="form-control">
                            <option>Central Crime Branch HQ (Rajkot)</option>
                            <option>Cyber Crime Investigation Cell</option>
                            <option>Anti-Extortion Special Squad</option>
                            <option>Narcotics Control Division</option>
                        </select>
                    </div>
                    <button type="submit" class="btn-gold">Authenticate &amp; Enter Portal &rarr;</button>
                </form>
            </div>

            <!-- Portal Overview Card -->
            <div class="card">
                <div class="card-header">
                    <h3>Enterprise Architecture</h3>
                    <span style="font-size: 12px; color: var(--gold);">AJT Full Stack</span>
                </div>
                <ul class="features-list">
                    <li><span>1. Swing Client:</span> High-performance desktop station GUI with 3-angle mugshots, offline persistence, and instant judicial search.</li>
                    <li><span>2. Java Servlets:</span> Controller layer handling session authentication, request validation, and multipart data processing.</li>
                    <li><span>3. JSP Views:</span> Dynamic web UI with JSTL taglibs, expression language (EL), and real-time status badges.</li>
                    <li><span>4. Spring REST API:</span> Modular service and repository endpoints for programmatic criminal queries and FIR filing.</li>
                </ul>

                <div class="stat-grid">
                    <div class="stat-item">
                        <div class="stat-num">3+</div>
                        <div class="stat-lbl">Active Dossiers</div>
                    </div>
                    <div class="stat-item">
                        <div class="stat-num">100%</div>
                        <div class="stat-lbl">FIR Coverage</div>
                    </div>
                    <div class="stat-item">
                        <div class="stat-num">4</div>
                        <div class="stat-lbl">AJT Techs</div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="footer">
        &copy; 2026 Rajkot Crime Branch • Advance Java Technology (AJT - 01CE0308) • Department of Computer Engineering
    </div>
</body>
</html>
