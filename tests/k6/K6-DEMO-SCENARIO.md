# 🎬 K6 DEMO SCENARIO - Movie Application Load Testing

## 📋 **KỊCH BẢN DEMO HOÀN CHỈNH**

### **🎯 Mục tiêu Demo:**
- Demonstrate K6 load testing capabilities
- Show real-world performance testing workflow  
- Test Movie Application under various load conditions
- Analyze performance bottlenecks and system limits

---

## **🚀 PHASE 1: PREPARATION (5 phút)**

### **Step 1.1: Verify Environment**
```powershell
# Kiểm tra ứng dụng đang chạy
curl http://localhost:8080

# Kiểm tra K6 có sẵn
k6 version

# Nếu không có K6 global, dùng local binary
.\k6-v0.47.0-windows-amd64\k6.exe version
```

### **Step 1.2: Quick App Tour**
```powershell
# Demo các endpoints sẽ được test
echo "Testing these endpoints:"
echo "✅ Homepage: http://localhost:8080/"
echo "✅ Movies: http://localhost:8080/movies/home"  
echo "✅ Search: http://localhost:8080/movies?keyword=action"
echo "✅ Details: http://localhost:8080/movies/getmoviebyid?id=1"
echo "✅ Login: http://localhost:8080/login"
```

---

## **🧪 PHASE 2: BASIC TESTING (10 phút)**

### **Step 2.1: Smoke Test - "Is the system alive?"**
```powershell
echo "🔥 SMOKE TEST: Minimal load to verify basic functionality"
k6 run --vus 1 --duration 30s tests\k6\k6-simple-test.js
```

**Giải thích kết quả:**
- `http_req_duration`: Thời gian phản hồi trung bình
- `http_req_failed`: Tỷ lệ request thất bại (phải = 0%)  
- `iterations`: Số lượng test cases đã chạy

### **Step 2.2: Load Test - "Normal usage simulation"**
```powershell
echo "📈 LOAD TEST: Simulating 10 concurrent users"
k6 run --vus 10 --duration 2m tests\k6\k6-simple-test.js
```

**Quan sát:**
- Response time có tăng không?
- Có request nào fail không?
- System handle 10 users như thế nào?

---

## **⚡ PHASE 3: STRESS TESTING (15 phút)**

### **Step 3.1: Ramp-up Test - "Finding the breaking point"**
```powershell
echo "🚀 STRESS TEST: Gradually increasing load"
k6 run tests\k6\k6-load-test.js
```

**Kịch bản này sẽ:**
1. **Smoke**: 1 user trong 30s
2. **Load**: Tăng từ 0→20 users trong 2 phút  
3. **Stress**: Tăng từ 0→100 users trong 5 phút
4. **Spike**: Đột ngột lên 200 users trong 1 phút

### **Step 3.2: API Endpoint Analysis**
```powershell
echo "🔍 API TESTING: Comprehensive endpoint validation"
k6 run tests\k6\k6-api-test.js
```

---

## **📊 PHASE 4: RESULTS ANALYSIS (10 phút)**

### **Step 4.1: Key Metrics Explanation**

**🔴 Performance Indicators:**
```bash
✓ http_req_duration.......avg=150ms  p(95)=300ms  # Phản hồi nhanh
✓ http_req_failed.........0.00%                   # Không có lỗi  
✓ http_reqs...............1,200/min               # Throughput cao
✓ vus.....................50                     # Concurrent users
```

### **Step 4.2: Thresholds Validation**
```powershell
echo "🎯 CHECKING THRESHOLDS:"
echo "Response Time: p(95) < 1000ms ✅"  
echo "Error Rate: < 5% ✅"
echo "Throughput: > 100 req/min ✅"
```

### **Step 4.3: Bottleneck Identification**
```powershell
echo "🔍 POTENTIAL BOTTLENECKS:"
echo "1. Database queries (slow movie search)"
echo "2. Session management (login/logout)"  
echo "3. File serving (static assets)"
echo "4. Memory usage (concurrent users)"
```

---

## **🚨 PHASE 5: FAILURE SCENARIOS (10 phút)**

### **Step 5.1: Overload Test - "Breaking the system"**
```powershell
echo "💥 OVERLOAD TEST: Pushing beyond limits"
k6 run --vus 500 --duration 30s tests\k6\k6-simple-test.js
```

**Mong đợi thấy:**
- High response times (>5000ms)
- Request failures (>50%)
- Possible timeouts
- System resource exhaustion

### **Step 5.2: Recovery Test**
```powershell
echo "🔄 RECOVERY TEST: Back to normal load"
timeout /t 60 /nobreak
k6 run --vus 10 --duration 1m tests\k6\k6-simple-test.js
```

**Quan sát:**
- Hệ thống có phục hồi không?
- Response time về bình thường?
- Có memory leaks không?

---

## **🎯 PHASE 6: CUSTOM SCENARIOS (5 phút)**

### **Step 6.1: Search Heavy Load**
```powershell
echo "🔍 SEARCH-FOCUSED TEST: Movie search performance"
k6 run --env SCENARIO=search_heavy tests\k6\k6-api-test.js
```

### **Step 6.2: Login Storm**
```powershell
echo "🔐 LOGIN STORM: Authentication system stress"  
k6 run --env SCENARIO=login_heavy tests\k6\k6-api-test.js
```

---

## **📈 DEMO SCRIPT TEMPLATE**

### **Presenter Commands:**
```powershell
# Demo bắt đầu
echo "🎬 K6 Load Testing Demo - Movie Application"
echo "=========================================="

# Test 1: Smoke Test
echo "Test 1: Verifying basic functionality..."
k6 run --vus 1 --duration 30s tests\k6\k6-simple-test.js
pause

# Test 2: Load Test  
echo "Test 2: Normal user load simulation..."
k6 run --vus 10 --duration 1m tests\k6\k6-simple-test.js
pause

# Test 3: Stress Test
echo "Test 3: Stress testing with ramp-up..."
k6 run tests\k6\k6-load-test.js
pause

# Test 4: Breaking Point
echo "Test 4: Finding the breaking point..."
k6 run --vus 100 --duration 30s tests\k6\k6-simple-test.js
pause

echo "🎯 Demo completed! Questions?"
```

---

## **🎤 TALKING POINTS FOR DEMO**

### **Opening (2 phút):**
- "Today we'll demonstrate load testing với K6"
- "We're testing a Spring Boot movie application"  
- "Goal: Find performance limits và bottlenecks"

### **During Tests (20 phút):**
- "Watch the response times increasing..."
- "Notice the throughput metrics..."
- "See how errors start appearing at high load..."
- "This pattern is typical for web applications..."

### **Conclusion (3 phút):**
- "K6 helps identify performance issues early"
- "Load testing should be part of CI/CD pipeline"
- "Performance thresholds prevent production issues"

---

## **📋 DEMO CHECKLIST**

**Before Demo:**
- [ ] ✅ Application running on localhost:8080
- [ ] ✅ K6 tool available and tested  
- [ ] ✅ Database populated with test data
- [ ] ✅ Network connection stable
- [ ] ✅ Screen sharing/projector ready

**During Demo:**
- [ ] 📊 Show live metrics và charts
- [ ] 🔍 Explain key performance indicators  
- [ ] 💡 Point out interesting patterns
- [ ] ❓ Answer questions về load testing

**After Demo:**
- [ ] 📝 Share test scripts với audience
- [ ] 🔗 Provide K6 documentation links
- [ ] 💼 Discuss integration into development workflow

---

## **🛠️ TROUBLESHOOTING TIPS**

**Nếu test thất bại:**
```powershell
# Kiểm tra app status
curl -I http://localhost:8080

# Giảm số lượng VUs
k6 run --vus 5 --duration 30s tests\k6\k6-simple-test.js

# Check system resources
Get-Process java
```

**Nếu response time cao:**
- "This is normal under high load"
- "Real applications need optimization"  
- "Database tuning usually helps most"

**Nếu có errors:**
- "Errors show system limits"
- "Production apps need error handling"
- "Circuit breakers prevent cascading failures"

---

## **🎯 KEY TAKEAWAYS**

1. **K6 is developer-friendly** - JavaScript-based, easy scripting
2. **Load testing reveals hidden issues** - Memory leaks, slow queries
3. **Performance thresholds are critical** - Automated pass/fail criteria  
4. **Early testing saves money** - Fix issues before production
5. **CI/CD integration is essential** - Automated performance regression detection

**Next steps: Integrate K6 into your development pipeline! 🚀**