Write-Host "========================================================" -ForegroundColor Cyan
Write-Host "      RAJKOT CRIME BRANCH - POLICE MANAGEMENT SYSTEM     " -ForegroundColor Yellow
Write-Host "========================================================" -ForegroundColor Cyan
Write-Host ""

if (-not (Test-Path "bin")) {
    New-Item -ItemType Directory -Path "bin" | Out-Null
}

Write-Host "[1/2] Compiling Java source files..." -ForegroundColor Green
javac -encoding UTF-8 -d bin (Get-ChildItem -Recurse -Filter *.java src | Select-Object -ExpandProperty FullName)

if ($LASTEXITCODE -ne 0) {
    Write-Host "[ERROR] Compilation failed!" -ForegroundColor Red
    exit $LASTEXITCODE
}

Write-Host "[2/2] Launching Application..." -ForegroundColor Green
java -cp bin com.police.Main
