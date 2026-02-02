# K6 Load Testing cho Movie Application

## 📋 Yêu cầu

1. **Cài đặt K6**: 
   ```bash
   # Windows (với Chocolatey)
   choco install k6
   
   # Hoặc tải từ: https://k6.io/docs/getting-started/installation/
   ```

2. **Ứng dụng phải đang chạy** trên `http://localhost:8080`

## 🚀 Cách chạy test

### 1. Test đơn giản (Khuyến nghị cho lần đầu)
```bash
k6 run k6-simple-test.js
```

### 2. Test chi tiết với nhiều scenario
```bash
k6 run k6-load-test.js
```

### 3. Chạy chỉ smoke test
```bash
k6 run --tag test_type=smoke k6-load-test.js
```

### 4. Chạy với số lượng users tùy chỉnh
```bash
k6 run --vus 10 --duration 30s k6-simple-test.js
```

## 📊 Các loại test trong file chính

1. **Smoke Test**: 1 user trong 30s - kiểm tra cơ bản
2. **Load Test**: Tăng dần từ 0→10→20 users - test tải bình thường  
3. **Stress Test**: Tăng dần lên 100 users - test khả năng chịu áp lực

## 🎯 Các endpoint được test

- ✅ Trang chủ (`/`)
- ✅ Danh sách phim (`/movies/home`)
- ✅ Tìm kiếm phim (`/movies?keyword=...`)
- ✅ Chi tiết phim (`/movies/getmoviebyid?id=...`)
- ✅ Trang đăng nhập (`/login`)
- ✅ Trang đăng ký (`/register`)
- ✅ Chức năng đăng nhập (POST `/login`)

## 📈 Ngưỡng thành công

- **Response Time**: 95% requests < 500ms (load-test) hoặc < 1s (simple-test)
- **Error Rate**: < 10% (load-test) hoặc < 5% (simple-test)
- **Check Success**: > 90%

## 🔧 Tùy chỉnh test

### Thay đổi số lượng user và thời gian:
```javascript
export let options = {
  vus: 10,        // 10 virtual users
  duration: '5m', // Chạy trong 5 phút
};
```

### Thêm endpoint mới để test:
```javascript
let newEndpoint = http.get(`${BASE_URL}/your-new-endpoint`);
check(newEndpoint, {
  'New endpoint works': (r) => r.status === 200,
});
```

## 📊 Đọc kết quả

Sau khi chạy xong, K6 sẽ hiển thị:

- **http_req_duration**: Thời gian response trung bình, min, max, p95
- **http_req_failed**: Tỉ lệ request bị lỗi
- **http_reqs**: Tổng số requests đã gửi  
- **vus**: Số virtual users đang hoạt động
- **checks**: Tỉ lệ check thành công

### Ví dụ kết quả tốt:
```
✓ Homepage status OK..................: 100.00%
✓ Movies page status is 200...........: 100.00% 
✓ Search works........................: 100.00%

http_req_duration..............: avg=245ms min=89ms med=201ms max=1.2s p(95)=456ms
http_req_failed................: 0.00%   ✓ 0        ✗ 1200
http_reqs......................: 1200    19.98/s
```

## 🚨 Troubleshooting

1. **Lỗi "connection refused"**: Kiểm tra ứng dụng có đang chạy trên port 8080 không
2. **Response time cao**: Kiểm tra database connection, tối ưu queries
3. **Error rate cao**: Kiểm tra logs ứng dụng để tìm nguyên nhân

## 💡 Tips

- Bắt đầu với test đơn giản trước
- Quan sát CPU/Memory usage khi chạy stress test
- Chạy test trong môi trường gần giống production
- Backup database trước khi chạy test với nhiều users