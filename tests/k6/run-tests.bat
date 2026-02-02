@echo off
echo 🚀 K6 Test Runner
echo =================

echo Available tests:
echo 1. Simple Test (5 users, 2 minutes)
echo 2. Load Test (Multiple scenarios)  
echo 3. API Test (Comprehensive)

set /p choice="Choose test (1-3): "

if "%choice%"=="1" (
    echo Running Simple Test...
    ..\k6-v0.47.0-windows-amd64\k6.exe run k6-simple-test.js
) else if "%choice%"=="2" (
    echo Running Load Test...
    ..\k6-v0.47.0-windows-amd64\k6.exe run k6-load-test.js
) else if "%choice%"=="3" (
    echo Running API Test...
    ..\k6-v0.47.0-windows-amd64\k6.exe run k6-api-test.js
) else (
    echo Invalid choice!
)

pause