@echo off
title Rajkot Crime Branch - Police Management System
echo ========================================================
echo       RAJKOT CRIME BRANCH - POLICE MANAGEMENT SYSTEM
echo ========================================================
echo.
echo [1/2] Compiling Java source files...
if not exist "bin" mkdir bin
javac -encoding UTF-8 -d bin src/com/police/model/*.java src/com/police/data/*.java src/com/police/ui/*.java src/com/police/Main.java

if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Compilation failed! Please check Java installation.
    pause
    exit /b %ERRORLEVEL%
)

echo [2/2] Launching Police Management System...
echo.
java -cp bin com.police.Main

pause
