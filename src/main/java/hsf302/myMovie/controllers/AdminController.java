package hsf302.myMovie.controllers;

import hsf302.myMovie.models.User;
import hsf302.myMovie.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAll());
        return "adminUserList";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") int id) {
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/editUser")
    public String editUser(@RequestParam("id") int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping("/editUser")
    public String updateUser(@RequestParam("id") int id,
                             @RequestParam("userName") String userName,
                             @RequestParam("fullName") String fullName,
                             @RequestParam("email") String email,
                             @RequestParam("role") int role) {
        userService.updateUser(id, userName, fullName, email, role);
        return "redirect:/admin/users";
    }

    // 🆕 Thêm user mới
    @GetMapping("/addUser")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "addUser";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestParam("userName") String userName,
                          @RequestParam("fullName") String fullName,
                          @RequestParam("email") String email,
                          @RequestParam("password") String password,
                          @RequestParam("role") int role) {
        userService.addUser(userName, fullName, email, password, role);
        return "redirect:/admin/users";
    }
}
