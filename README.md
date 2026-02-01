# 🎬 Movie Management Application

Ứng dụng quản lý phim được xây dựng với Spring Boot, Hibernate và Thymeleaf. Hệ thống cho phép quản lý danh sách phim, đánh giá, bình luận và tài khoản người dùng.

## ✨ Tính năng chính

### 👥 Quản lý người dùng
- ✅ Đăng ký/Đăng nhập
- ✅ Phân quyền Admin/User
- ✅ Quản lý hồ sơ người dùng

### 🎞️ Quản lý phim
- ✅ Xem danh sách phim với thumbnail
- ✅ Tìm kiếm phim theo tên
- ✅ Xem chi tiết phim (trailer, mô tả, đánh giá)
- ✅ Thêm phim vào danh sách yêu thích
- ✅ Admin có thể CRUD phim

### 💬 Hệ thống tương tác
- ✅ Bình luận phim
- ✅ Đánh giá phim
- ✅ Xem phim trực tuyến

### 🌍 Phân loại
- ✅ Quản lý thể loại phim (Genre)
- ✅ Quản lý quốc gia sản xuất (Country)
- ✅ Lọc phim theo thể loại/quốc gia

## 🛠️ Công nghệ sử dụng

### Backend
- **Spring Boot 3.4.3** - Framework chính
- **Spring Data JPA** - ORM và database access
- **Hibernate** - ORM implementation
- **Spring MVC** - Web framework
- **Thymeleaf** - Template engine

### Database
- **SQL Server** - Database chính
- **HikariCP** - Connection pooling

### Frontend
- **Thymeleaf** - Server-side rendering
- **HTML5/CSS3** - UI styling
- **Bootstrap** (optional) - Responsive design

### Testing & Performance
- **K6** - Load testing framework
- **JUnit** - Unit testing

## 📋 Yêu cầu hệ thống

- **Java 17+**
- **Maven 3.6+**
- **SQL Server** (hoặc database tương thích)
- **K6** (cho load testing)

## 🚀 Hướng dẫn cài đặt

### 1. Clone repository
```bash
git clone https://github.com/your-username/movie-management.git
cd movie-management
```

### 2. Cấu hình database
Sửa file `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=MyMovie;encrypt=true;trustServerCertificate=true
spring.datasource.username=sa
spring.datasource.password=your_password
```

### 3. Chạy ứng dụng
```bash
# Compile project
mvn clean compile

# Chạy ứng dụng
mvn spring-boot:run
```

### 4. Truy cập ứng dụng
- URL: `http://localhost:8080`
- Admin account: `admin/admin123`

## 📊 Load Testing với K6

Project bao gồm các script K6 để test performance:

### Quick Test
```bash
k6 run k6-simple-test.js
```

### Comprehensive Test
```bash
k6 run k6-load-test.js
```

### API Testing
```bash
k6 run k6-api-test.js
```

📖 **Chi tiết**: Xem [K6-SIMPLE-TEST-GUIDE.md](K6-SIMPLE-TEST-GUIDE.md)

## 📁 Cấu trúc project

```
movie123/
├── src/
│   ├── main/
│   │   ├── java/hsf302/myMovie/
│   │   │   ├── controllers/     # REST Controllers
│   │   │   ├── models/          # Entity classes
│   │   │   ├── repo/           # Repository interfaces
│   │   │   ├── services/       # Business logic
│   │   │   └── config/         # Configuration & Data init
│   │   └── resources/
│   │       ├── templates/      # Thymeleaf templates
│   │       ├── static/         # CSS, JS files
│   │       └── application.properties
│   └── test/                   # Unit tests
├── k6-*.js                     # K6 test scripts
├── K6-SIMPLE-TEST-GUIDE.md     # K6 testing guide
├── K6_README.md                # K6 setup guide
└── pom.xml                     # Maven dependencies
```

## 🔑 Tài khoản mặc định

### Admin
- Username: `admin`
- Password: `admin123`
- Quyền: Quản lý toàn bộ hệ thống

### User Test
- Username: `user1`
- Password: `123456`
- Quyền: Xem phim, bình luận, yêu thích

## 📸 Screenshots

### Trang chủ
![Homepage](docs/images/homepage.png)

### Chi tiết phim
![Movie Detail](docs/images/movie-detail.png)

### Admin Panel
![Admin Panel](docs/images/admin-panel.png)

## 🤝 Đóng góp

1. Fork project
2. Tạo feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Tạo Pull Request

## 📝 License

Distributed under the MIT License. See `LICENSE` for more information.

## 👨‍💻 Tác giả

- **Your Name** - [your-email@example.com](mailto:your-email@example.com)
- GitHub: [@your-username](https://github.com/your-username)

## 🙏 Acknowledgments

- Spring Boot Documentation
- Thymeleaf Documentation
- K6 Performance Testing Framework
- Stack Overflow Community