import http from 'k6/http';
import { check, sleep, group } from 'k6';
import { Rate } from 'k6/metrics';

// Tùy chọn cấu hình test
export let options = {
  scenarios: {
    // Scenario 1: Smoke test - kiểm tra cơ bản
    smoke: {
      executor: 'constant-vus',
      vus: 1,
      duration: '30s',
      tags: { test_type: 'smoke' },
    },
    
    // Scenario 2: Load test - test tải bình thường
    load: {
      executor: 'ramping-vus',
      startVUs: 0,
      stages: [
        { duration: '2m', target: 10 },  // Tăng dần lên 10 users trong 2 phút
        { duration: '5m', target: 10 },  // Giữ 10 users trong 5 phút
        { duration: '2m', target: 20 },  // Tăng lên 20 users trong 2 phút
        { duration: '5m', target: 20 },  // Giữ 20 users trong 5 phút
        { duration: '2m', target: 0 },   // Giảm xuống 0 users trong 2 phút
      ],
      tags: { test_type: 'load' },
    },
    
    // Scenario 3: Stress test - test áp lực cao
    stress: {
      executor: 'ramping-vus',
      startVUs: 0,
      stages: [
        { duration: '2m', target: 20 },  // Tăng lên 20 users
        { duration: '5m', target: 20 },  // Giữ 20 users
        { duration: '2m', target: 50 },  // Tăng lên 50 users
        { duration: '5m', target: 50 },  // Giữ 50 users
        { duration: '2m', target: 100 }, // Tăng lên 100 users
        { duration: '5m', target: 100 }, // Giữ 100 users
        { duration: '2m', target: 0 },   // Giảm xuống 0
      ],
      tags: { test_type: 'stress' },
    }
  },
  
  // Ngưỡng thành công
  thresholds: {
    http_req_duration: ['p(95)<500'], // 95% requests phải < 500ms
    http_req_failed: ['rate<0.1'],    // Tỉ lệ lỗi < 10%
    checks: ['rate>0.9'],             // 90% checks phải pass
  },
};

// Cấu hình base URL
const BASE_URL = 'http://localhost:8080';

// Custom metrics
const errorRate = new Rate('errors');

// Hàm test trang chủ
function testHomePage() {
  group('Homepage Tests', function () {
    let response = http.get(`${BASE_URL}/`);
    
    check(response, {
      'Homepage status is 200 or redirect': (r) => r.status === 200 || r.status === 302,
      'Homepage response time < 1s': (r) => r.timings.duration < 1000,
    });
    
    if (response.status !== 200 && response.status !== 302) {
      errorRate.add(1);
    } else {
      errorRate.add(0);
    }
  });
}

// Hàm test danh sách phim
function testMovieList() {
  group('Movie List Tests', function () {
    let response = http.get(`${BASE_URL}/movies/home`);
    
    check(response, {
      'Movie list status is 200': (r) => r.status === 200,
      'Movie list response time < 1s': (r) => r.timings.duration < 1000,
      'Movie list contains movies': (r) => r.body.includes('movie-card') || r.body.includes('Movie List'),
    });
    
    if (response.status !== 200) {
      errorRate.add(1);
    } else {
      errorRate.add(0);
    }
  });
}

// Hàm test tìm kiếm phim
function testMovieSearch() {
  group('Movie Search Tests', function () {
    const searchKeywords = ['action', 'drama', 'comedy'];
    const keyword = searchKeywords[Math.floor(Math.random() * searchKeywords.length)];
    
    let response = http.get(`${BASE_URL}/movies?keyword=${keyword}`);
    
    check(response, {
      'Search status is 200': (r) => r.status === 200,
      'Search response time < 2s': (r) => r.timings.duration < 2000,
    });
    
    if (response.status !== 200) {
      errorRate.add(1);
    } else {
      errorRate.add(0);
    }
  });
}

// Hàm test chi tiết phim
function testMovieDetails() {
  group('Movie Details Tests', function () {
    // Test với movie ID từ 1-10 (giả sử có data)
    const movieId = Math.floor(Math.random() * 10) + 1;
    let response = http.get(`${BASE_URL}/movies/getmoviebyid?id=${movieId}`);
    
    check(response, {
      'Movie details status is 200 or redirect': (r) => r.status === 200 || r.status === 302,
      'Movie details response time < 1s': (r) => r.timings.duration < 1000,
    });
    
    if (response.status !== 200 && response.status !== 302) {
      errorRate.add(1);
    } else {
      errorRate.add(0);
    }
  });
}

// Hàm test trang đăng nhập
function testLoginPage() {
  group('Login Page Tests', function () {
    let response = http.get(`${BASE_URL}/login`);
    
    check(response, {
      'Login page status is 200': (r) => r.status === 200,
      'Login page response time < 1s': (r) => r.timings.duration < 1000,
      'Login page contains form': (r) => r.body.includes('login') || r.body.includes('password'),
    });
    
    if (response.status !== 200) {
      errorRate.add(1);
    } else {
      errorRate.add(0);
    }
  });
}

// Hàm test đăng nhập thực tế
function testLogin() {
  group('Login Functionality Tests', function () {
    // Lấy trang login trước để có session
    let loginPage = http.get(`${BASE_URL}/login`);
    
    // Thử đăng nhập với tài khoản test
    let loginData = {
      emailOrUsername: 'admin',
      password: 'admin123'
    };
    
    let response = http.post(`${BASE_URL}/login`, loginData, {
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    });
    
    check(response, {
      'Login response received': (r) => r.status === 200 || r.status === 302,
      'Login response time < 2s': (r) => r.timings.duration < 2000,
    });
    
    if (response.status !== 200 && response.status !== 302) {
      errorRate.add(1);
    } else {
      errorRate.add(0);
    }
  });
}

// Hàm test trang đăng ký
function testRegisterPage() {
  group('Register Page Tests', function () {
    let response = http.get(`${BASE_URL}/register`);
    
    check(response, {
      'Register page status is 200': (r) => r.status === 200,
      'Register page response time < 1s': (r) => r.timings.duration < 1000,
      'Register page contains form': (r) => r.body.includes('register') || r.body.includes('userName'),
    });
    
    if (response.status !== 200) {
      errorRate.add(1);
    } else {
      errorRate.add(0);
    }
  });
}

// Hàm chính - chạy các test scenario
export default function () {
  // Chọn ngẫu nhiên một test để chạy
  const testFunctions = [
    testHomePage,
    testMovieList, 
    testMovieSearch,
    testMovieDetails,
    testLoginPage,
    testRegisterPage
  ];
  
  // Chạy 2-3 test functions ngẫu nhiên mỗi iteration
  const numberOfTests = Math.floor(Math.random() * 2) + 2;
  
  for (let i = 0; i < numberOfTests; i++) {
    const randomTest = testFunctions[Math.floor(Math.random() * testFunctions.length)];
    randomTest();
    
    // Nghỉ ngắn giữa các requests (1-3 giây)
    sleep(Math.random() * 2 + 1);
  }
  
  // Test login thỉnh thoảng (10% chance)
  if (Math.random() < 0.1) {
    testLogin();
  }
}

// Hàm setup - chạy trước khi bắt đầu test
export function setup() {
  console.log('🚀 Bắt đầu K6 Load Test cho Movie Application');
  console.log(`📍 Target URL: ${BASE_URL}`);
  console.log('📊 Checking if application is running...');
  
  let response = http.get(`${BASE_URL}/`);
  if (response.status !== 200 && response.status !== 302) {
    throw new Error(`❌ Application is not responding. Status: ${response.status}`);
  }
  
  console.log('✅ Application is running and responsive');
  return { baseUrl: BASE_URL };
}

// Hàm teardown - chạy sau khi kết thúc test  
export function teardown(data) {
  console.log('🏁 K6 Load Test hoàn thành');
  console.log('📈 Kiểm tra kết quả chi tiết trong summary report');
}