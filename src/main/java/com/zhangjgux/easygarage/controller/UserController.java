package com.zhangjgux.easygarage.controller;

import com.zhangjgux.easygarage.entity.User;
import com.zhangjgux.easygarage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/users/{userID}")
    public User findById(@PathVariable int userID) {
        User res = userService.findById(userID);
        if (res == null) throw new RuntimeException("User not found");
        return res;
    }

    @GetMapping("/me")
    public User getCurrent() {
        return userService.getCurrent();
    }

    @PutMapping("/me")
    public User updateUser(@RequestBody Map<String, Object> body) {
        User theUser = userService.getUserByEmail((String) body.get("original_email"));
        if (theUser == null || (userService.getUserByEmail((String) body.get("new_email")) != null
                && !((String) body.get("new_email")).equals((String) body.get("original_email")))) {
            throw new RuntimeException("Repeated Email!");
        }
        theUser.setEmail((String) body.get("new_email"));
        theUser.setName((String) body.get("name"));
        theUser.setPassword(new BCryptPasswordEncoder().encode((String) body.get("password")));
        theUser.setDescription((String) body.get("description"));
        userService.save(theUser);
        return theUser;
    }

    @DeleteMapping("users/{userID}")
    public Map<String, String> deleteUser(@PathVariable int userID) {
        User user = userService.findById(userID);
        if (user == null) throw new RuntimeException("User not found");
        userService.deleteById(userID);
        Map<String, String> res = new HashMap<>();
        res.put("msg", "Successfully Deleted!");
        return res;
    }
}
