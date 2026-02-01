package hsf302.myMovie.controllers;

import hsf302.myMovie.models.User;
import hsf302.myMovie.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // Mapping cho root path
    @GetMapping("/")
    public String home() {
        return "redirect:/movies/home";
    }

    @GetMapping({ "/login"})
    public String showLoginPage() {
        return "login";
    }

    @GetMapping({"/logout"})
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam("emailOrUsername") String emailOrUsername, @RequestParam("password") String password, Model model, HttpSession session) {

        // Kiểm tra xem emailOrUsername có phải là email hợp lệ không
        User acc = null;
        if (emailOrUsername.contains("@")) {
            // Nếu có "@" là email, tìm người dùng bằng email
            acc = userService.findByEmail(emailOrUsername);
        } else {
            // Nếu không phải email, tìm người dùng bằng username
            acc = userService.findByUserName(emailOrUsername);
        }

        // Kiểm tra mật khẩu
        if (acc != null && acc.getPassword().equals(password)) {
            session.setAttribute("acc", acc);
            session.setAttribute("userRoleId", acc.getRole());
            return "redirect:/movies/home";
        } else {
            model.addAttribute("error", "Sai tài khoản đăng nhập hoặc mật khẩu!");
            return "login";
        }
    }

    @GetMapping({ "/register"})
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@RequestParam("userName") String userName,
                             @RequestParam("fullName") String fullName,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password,
                             Model model) {

        // Tạo mới người dùng
        User newUser = new User();
        newUser.setUserName(userName);
        newUser.setFullName(fullName);
        newUser.setEmail(email);
        newUser.setPassword(password); // Lưu mật khẩu thuần (không mã hóa)

        newUser.setRole(2); // Gán role mặc định là 2 (chưa admin)

        // Kiểm tra xem tên người dùng đã tồn tại chưa
        User acc = userService.findByUserName(userName);
        if (acc != null) {
            model.addAttribute("error1", "Tên người dùng đã tồn tại");
            return "register";
        }

        // Kiểm tra xem email đã tồn tại chưa
        acc = userService.findByEmail(email);
        if (acc != null) {
            model.addAttribute("error2", "Email đã tồn tại");
            return "register";
        }

        // Nếu không có lỗi, lưu người dùng vào cơ sở dữ liệu
        model.addAttribute("success", "Dang ky thanh cong");
        userService.save(newUser);

        // Chuyển hướng đến trang đăng nhập
        return "login";


    }




}



