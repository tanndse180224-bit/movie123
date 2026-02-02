# 📋 K6 Tests - Movie Application

## 📁 File Structure
```
tests/k6/
├── k6-simple-test.js      # Basic load test (5 users, 2 min)
├── k6-load-test.js        # Advanced scenarios 
├── k6-api-test.js         # API endpoint testing
├── run-tests.bat          # Interactive test runner
├── K6_README.md           # Documentation
└── K6-SIMPLE-TEST-GUIDE.md # Detailed guide
```

## 🚀 Quick Start

### Method 1: Using Interactive Runner
```bash
cd tests\k6
run-tests.bat
```

### Method 2: Direct Commands
```bash
# From project root
k6 run tests\k6\k6-simple-test.js
k6 run tests\k6\k6-load-test.js
k6 run tests\k6\k6-api-test.js

# From tests/k6/ directory  
cd tests\k6
k6 run k6-simple-test.js
```

### Method 3: Using Main Batch File (Auto-starts app)
```bash
# From project root
run-k6-test.bat
```

## 📊 Test Types

- **Simple Test**: Basic performance check
- **Load Test**: Multiple scenarios with ramp-up
- **API Test**: Comprehensive endpoint testing

## 🎯 Prerequisites

1. ✅ Application running on http://localhost:8080
2. ✅ K6 installed or use: `.\k6-v0.47.0-windows-amd64\k6.exe`

## 🔧 Customization

```bash
# Custom parameters
k6 run --vus 10 --duration 30s tests\k6\k6-simple-test.js
k6 run --tag test_type=smoke tests\k6\k6-load-test.js
```