package hsf302.myMovie.services;

import hsf302.myMovie.models.User;
import hsf302.myMovie.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public User findByUserName(String userName) {
        return userRepo.findByUserName(userName);
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);

    }

    public User findByid(int id) {
        return userRepo.findById(id);
    }

    public Object getAll() {
        return userRepo.findAll();
    }

    public void deleteUserById(int id) {
        userRepo.deleteById(id);
    }

    public User getUserById(int id) {
        return userRepo.findById(id);
    }

    public void updateUser(int id, String userName, String fullName, String email, int role) {
        User user = userRepo.findById(id);
        if (user != null) {
            user.setUserName(userName);
            user.setFullName(fullName);
            user.setEmail(email);
            user.setRole(role);
            userRepo.save(user);
        }
    }

    public void addUser(String userName, String fullName, String email, String password, int role) {
        User newUser = new User();
        newUser.setUserName(userName);
        newUser.setFullName(fullName);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setRole(role);
        userRepo.save(newUser);
    }


    public void save(User user) {
        userRepo.save(user);
    }
}
