import http from 'k6/http';
import { check, sleep } from 'k6';

// Cấu hình test đơn giản
export let options = {
  vus: 5,        // 5 virtual users
  duration: '2m', // Chạy trong 2 phút
  
  thresholds: {
    http_req_duration: ['p(95)<1000'], // 95% requests < 1s
    http_req_failed: ['rate<0.05'],    // Tỉ lệ lỗi < 5%
  },
};

const BASE_URL = 'http://localhost:8080';

export default function () {
  // Test trang chủ
  let homeResponse = http.get(`${BASE_URL}/`);
  check(homeResponse, {
    'Homepage status OK': (r) => r.status === 200 || r.status === 302,
  });
  
  sleep(1);
  
  // Test danh sách phim
  let moviesResponse = http.get(`${BASE_URL}/movies/home`);
  check(moviesResponse, {
    'Movies page status is 200': (r) => r.status === 200,
    'Movies page loads quickly': (r) => r.timings.duration < 2000,
  });
  
  sleep(1);
  
  // Test tìm kiếm phim
  let searchResponse = http.get(`${BASE_URL}/movies?keyword=action`);
  check(searchResponse, {
    'Search works': (r) => r.status === 200,
  });
  
  sleep(1);
  
  // Test chi tiết phim (movie ID 1)
  let detailResponse = http.get(`${BASE_URL}/movies/getmoviebyid?id=1`);
  check(detailResponse, {
    'Movie detail accessible': (r) => r.status === 200 || r.status === 302,
  });
  
  sleep(1);
}