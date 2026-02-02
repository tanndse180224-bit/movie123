@echo off
echo 🚀 Starting Spring Boot application...
start /B mvn spring-boot:run

echo ⏳ Waiting for application to start (30 seconds)...
timeout /t 30 /nobreak

echo 🧪 Running K6 load tests...
.\k6-v0.47.0-windows-amd64\k6.exe run tests\k6\k6-simple-test.js

echo ✅ K6 tests completed. Press any key to stop Spring Boot...
pause

echo 🛑 Stopping Spring Boot application...
taskkill /f /im java.exe

echo 🏁 Test completed!
pause