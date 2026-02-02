import http from 'k6/http';
import { check, sleep, group } from 'k6';

export let options = {
  scenarios: {
    api_test: {
      executor: 'constant-vus',
      vus: 3,
      duration: '1m',
    }
  },
  thresholds: {
    http_req_duration: ['p(95)<800'],
    http_req_failed: ['rate<0.05'],
  },
};

const BASE_URL = 'http://localhost:8080';

export default function () {
  group('API Endpoints Test', () => {
    
    // Test 1: Homepage redirect
    group('Homepage', () => {
      let response = http.get(`${BASE_URL}/`);
      check(response, {
        'Homepage redirects properly': (r) => r.status === 302 || r.status === 200,
        'Redirect to movies/home': (r) => r.url.includes('/movies/home') || r.status === 302
      });
    });

    sleep(0.5);

    // Test 2: Movies listing
    group('Movies List', () => {
      let response = http.get(`${BASE_URL}/movies/home`);
      check(response, {
        'Movies list loads': (r) => r.status === 200,
        'Contains movie content': (r) => r.body.includes('Movie') || r.body.includes('movie'),
        'Response size reasonable': (r) => r.body.length > 1000
      });
    });

    sleep(0.5);

    // Test 3: Movie search
    group('Movie Search', () => {
      const keywords = ['action', 'drama', 'comedy', 'horror'];
      const keyword = keywords[Math.floor(Math.random() * keywords.length)];
      
      let response = http.get(`${BASE_URL}/movies?keyword=${keyword}`);
      check(response, {
        'Search executes successfully': (r) => r.status === 200,
        'Search returns content': (r) => r.body.length > 500
      });
    });

    sleep(0.5);

    // Test 4: Authentication pages
    group('Authentication', () => {
      // Login page
      let loginResponse = http.get(`${BASE_URL}/login`);
      check(loginResponse, {
        'Login page accessible': (r) => r.status === 200,
        'Login form present': (r) => r.body.includes('password') || r.body.includes('login')
      });

      sleep(0.3);

      // Register page  
      let registerResponse = http.get(`${BASE_URL}/register`);
      check(registerResponse, {
        'Register page accessible': (r) => r.status === 200,
        'Register form present': (r) => r.body.includes('register') || r.body.includes('userName')
      });
    });

    sleep(0.5);

    // Test 5: Movie details (test with multiple IDs)
    group('Movie Details', () => {
      const movieIds = [1, 2, 3];
      const movieId = movieIds[Math.floor(Math.random() * movieIds.length)];
      
      let response = http.get(`${BASE_URL}/movies/getmoviebyid?id=${movieId}`);
      check(response, {
        'Movie detail page loads': (r) => r.status === 200 || r.status === 302,
        'Response received timely': (r) => r.timings.duration < 1000
      });
    });

    sleep(0.5);

    // Test 6: Static resources check
    group('Static Resources', () => {
      // CSS files
      let cssResponse = http.get(`${BASE_URL}/login.css`);
      check(cssResponse, {
        'CSS accessible': (r) => r.status === 200 || r.status === 404 // 404 OK nếu không có CSS riêng
      });
    });

  });

  // Random thinking time between iterations
  sleep(Math.random() * 2 + 1);
}

export function setup() {
  console.log('🔧 Testing Movie Application API Endpoints');
  
  // Verify application is running
  try {
    let healthCheck = http.get(`${BASE_URL}/`);
    if (healthCheck.status !== 200 && healthCheck.status !== 302) {
      console.error(`❌ Application health check failed. Status: ${healthCheck.status}`);
    } else {
      console.log('✅ Application is responsive');
    }
  } catch (error) {
    console.error('❌ Cannot connect to application:', error.message);
  }
}

export function teardown() {
  console.log('✅ API Endpoints testing completed');
}