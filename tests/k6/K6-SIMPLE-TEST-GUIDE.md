# 📚 Hướng dẫn K6 Simple Test cho Movie Application

## 🎯 Tổng quan

File `k6-simple-test.js` là một script load testing đơn giản được thiết kế để kiểm tra performance cơ bản của ứng dụng Movie. Script này sẽ mô phỏng 5 người dùng đồng thời truy cập ứng dụng trong 2 phút.

## 📋 Yêu cầu trước khi chạy

1. **K6 đã được cài đặt**: Đảm bảo K6 có sẵn trong PATH hoặc trong thư mục hiện tại
2. **Ứng dụng Spring Boot đang chạy**: Ứng dụng Movie phải đang hoạt động trên `http://localhost:8080`

### Kiểm tra ứng dụng đang chạy:
```bash
# Kiểm tra port 8080
netstat -an | findstr ":8080"

# Nếu không có kết quả, khởi động ứng dụng:
mvn spring-boot:run
```
k6 run tests\k6\k6-simple-test.js

## 🔍 Giải thích chi tiết từng dòng code

### 1. Import các module cần thiết

```javascript
import http from 'k6/http';
import { check, sleep } from 'k6';
```

**Giải thích:**
- `http`: Module để gửi HTTP requests (GET, POST, PUT, DELETE...)
- `check`: Hàm để validate kết quả response (kiểm tra status code, response time, nội dung...)
- `sleep`: Hàm tạm dừng giữa các requests để mô phỏng hành vi người dùng thực

### 2. Cấu hình test options

```javascript
export let options = {
  vus: 5,        // 5 virtual users
  duration: '2m', // Chạy trong 2 phút
  
  thresholds: {
    http_req_duration: ['p(95)<1000'], // 95% requests < 1s
    http_req_failed: ['rate<0.05'],    // Tỉ lệ lỗi < 5%
  },
};
```

**Giải thích:**
- `vus: 5`: Chạy với 5 virtual users đồng thời (mô phỏng 5 người dùng)
- `duration: '2m'`: Test sẽ chạy trong 2 phút
- `thresholds`: Ngưỡng để đánh giá test thành công hay thất bại
  - `p(95)<1000`: 95% requests phải có response time < 1 giây
  - `rate<0.05`: Tỉ lệ requests lỗi phải < 5%

### 3. Cấu hình Base URL

```javascript
const BASE_URL = 'http://localhost:8080';
```

**Giải thích:**
- Định nghĩa URL gốc của ứng dụng
- Dễ dàng thay đổi khi deploy sang môi trường khác

### 4. Hàm test chính

```javascript
export default function () {
  // Test trang chủ
  let homeResponse = http.get(`${BASE_URL}/`);
  check(homeResponse, {
    'Homepage status OK': (r) => r.status === 200 || r.status === 302,
  });
  
  sleep(1);
  
  // ... các test khác
}
```

**Giải thích chi tiết từng test:**

#### Test 1: Trang chủ
```javascript
let homeResponse = http.get(`${BASE_URL}/`);
check(homeResponse, {
  'Homepage status OK': (r) => r.status === 200 || r.status === 302,
});
sleep(1);
```

- `http.get()`: Gửi GET request đến trang chủ
- `check()`: Kiểm tra response có status 200 (OK) hoặc 302 (Redirect)
- `sleep(1)`: Tạm dừng 1 giây để mô phỏng người dùng đọc trang

#### Test 2: Danh sách phim
```javascript
let moviesResponse = http.get(`${BASE_URL}/movies/home`);
check(moviesResponse, {
  'Movies page status is 200': (r) => r.status === 200,
  'Movies page loads quickly': (r) => r.timings.duration < 2000,
});
sleep(1);
```

- Kiểm tra trang danh sách phim
- Validate status code = 200
- Validate response time < 2 giây
- `r.timings.duration`: Thời gian response tính từ lúc gửi request đến lúc nhận response hoàn chỉnh

#### Test 3: Tìm kiếm phim
```javascript
let searchResponse = http.get(`${BASE_URL}/movies?keyword=action`);
check(searchResponse, {
  'Search works': (r) => r.status === 200,
});
sleep(1);
```

- Test chức năng tìm kiếm với từ khóa "action"
- Kiểm tra API search hoạt động bình thường

#### Test 4: Chi tiết phim
```javascript
let detailResponse = http.get(`${BASE_URL}/movies/getmoviebyid?id=1`);
check(detailResponse, {
  'Movie detail accessible': (r) => r.status === 200 || r.status === 302,
});
sleep(1);
```

- Test trang chi tiết phim với ID = 1
- Cho phép status 302 vì có thể redirect nếu không tìm thấy phim

## 🚀 Cách chạy test

### 1. Khởi động ứng dụng Spring Boot
```bash
mvn spring-boot:run
```
*Để terminal này chạy, mở terminal mới cho bước tiếp theo*

### 2. Chạy K6 test (terminal mới)
```bash
# Nếu K6 trong PATH
k6 run k6-simple-test.js

# Nếu K6 trong thư mục hiện tại
./k6.exe run k6-simple-test.js
```

## 📊 Cách đọc kết quả test

### Kết quả mẫu và giải thích:

```
     ✓ Homepage status OK
     ✓ Movies page status is 200  
     ✓ Movies page loads quickly
     ✓ Search works
     ✓ Movie detail accessible

     checks.........................: 100.00% ✓ 600      ✗ 0   
     data_received..................: 2.4 MB  20 kB/s
     data_sent......................: 48 kB   400 B/s
     http_req_blocked...............: avg=2.35ms   min=0s       med=1ms      max=284ms    p(90)=3ms      p(95)=4ms   
     http_req_connecting............: avg=1.01ms   min=0s       med=0s       max=101ms    p(90)=2ms      p(95)=3ms   
     http_req_duration..............: avg=245.2ms  min=12.4ms   med=198.5ms  max=1.2s     p(90)=456ms    p(95)=612ms 
       { expected_response:true }...: avg=245.2ms  min=12.4ms   med=198.5ms  max=1.2s     p(90)=456ms    p(95)=612ms 
     http_req_failed................: 0.00%   ✓ 0        ✗ 600 
     http_req_receiving.............: avg=1.2ms    min=89µs     med=879µs    max=89ms     p(90)=2.1ms    p(95)=3.2ms 
     http_req_sending...............: avg=245µs    min=21µs     med=156µs    max=12ms     p(90)=456µs    p(95)=789µs 
     http_req_tls_handshaking.......: avg=0s       min=0s       med=0s       max=0s       p(90)=0s       p(95)=0s    
     http_req_waiting...............: avg=243.7ms  min=12.1ms   med=197ms    max=1.19s    p(90)=453ms    p(95)=608ms 
     http_reqs......................: 600     5.0/s
     iteration_duration.............: avg=4.24s    min=4.02s    med=4.19s    max=5.39s    p(90)=4.46s    p(95)=4.68s 
     iterations.....................: 120     1.0/s
     vus............................: 5       min=5      max=5 
     vus_max........................: 5       min=5      max=5 
```

### 🔍 Giải thích các metrics quan trọng:

#### ✅ **Checks (Kiểm tra validation)**
- `✓ 600 ✗ 0` = 600 checks thành công, 0 checks thất bại
- `100.00%` = Tỉ lệ thành công 100%

#### 📡 **Data Transfer**
- `data_received: 2.4 MB 20 kB/s` = Nhận về 2.4MB data với tốc độ 20KB/s
- `data_sent: 48 kB 400 B/s` = Gửi đi 48KB data với tốc độ 400 bytes/s

#### ⏱️ **Response Time (http_req_duration)**
- `avg=245.2ms` = Thời gian response trung bình: 245ms
- `min=12.4ms` = Response nhanh nhất: 12.4ms  
- `max=1.2s` = Response chậm nhất: 1.2s
- `p(95)=612ms` = 95% requests có response time ≤ 612ms

#### ❌ **Error Rate (http_req_failed)**
- `0.00%` = Không có request nào bị lỗi
- `✓ 0 ✗ 600` = 0 lỗi trong tổng 600 requests

#### 🔄 **Request Statistics**
- `http_reqs: 600 5.0/s` = Tổng 600 requests, 5 requests/giây
- `iterations: 120 1.0/s` = 120 lần chạy function chính, 1 iteration/giây
- `vus: 5` = Đang chạy với 5 virtual users

## 🎯 Đánh giá kết quả

### ✅ **Test THÀNH CÔNG** khi:
- `checks: 100.00%` hoặc > 95%
- `http_req_failed: 0.00%` hoặc < 5%  
- `http_req_duration p(95)` < ngưỡng đặt ra (1000ms)
- Không có lỗi connection timeout

### ❌ **Test THẤT BẠI** khi:
- `checks` < 90%
- `http_req_failed` > 5%
- `http_req_duration p(95)` > ngưỡng
- Có nhiều errors trong console

### ⚠️ **Cần TỐI ƯU** khi:
- Response time cao (p(95) > 500ms)
- Data transfer rate thấp
- Connection time cao (http_req_connecting > 100ms)

## 🔧 Troubleshooting

### Lỗi thường gặp:

#### 1. `Connection refused`
```
ERRO[0001] GoError: Get "http://localhost:8080/": dial tcp [::1]:8080: connectex: 
No connection could be made because the target machine actively refused it.
```

**Nguyên nhân:** Ứng dụng Spring Boot không đang chạy  
**Giải pháp:** Khởi động ứng dụng với `mvn spring-boot:run`

#### 2. `Threshold failed`
```
✗ http_req_duration: p(95)<1000 threshold failed: p(95)=1245.67ms
```

**Nguyên nhân:** Response time quá chậm  
**Giải pháp:** Tối ưu database queries, tăng resources server

#### 3. `High error rate`
```
✗ http_req_failed: rate<0.05 threshold failed: rate=0.12
```

**Nguyên nhân:** Nhiều requests bị lỗi  
**Giải pháp:** Kiểm tra logs ứng dụng, fix bugs

## 💡 Tips để có kết quả test tốt

1. **Warm-up ứng dụng** trước khi test (truy cập 1-2 trang để JVM khởi động)
2. **Đóng browser** và ứng dụng khác để tránh ảnh hưởng resources
3. **Chạy test nhiều lần** để có kết quả ổn định
4. **Monitor CPU/Memory** trong quá trình test
5. **Backup database** trước khi chạy stress test

## 📈 Nâng cao

Để test chi tiết hơn, sử dụng:
- `k6-load-test.js` - Test với nhiều scenarios
- `k6-api-test.js` - Test focused vào API endpoints

```bash
k6 run k6-load-test.js
k6 run k6-api-test.js
```