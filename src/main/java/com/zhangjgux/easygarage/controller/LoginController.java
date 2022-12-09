package com.zhangjgux.easygarage.controller;

import com.zhangjgux.easygarage.entity.User;
import com.zhangjgux.easygarage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, Object> body) throws Exception {

        Authentication authObject = null;
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken((String) body.get("email"), (String) body.get("password")));
            SecurityContextHolder.getContext().setAuthentication(authObject);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Failed login");
        }

        Map<String, String> response = new HashMap<>();
        response.put("msg", "Successfully Registered!");
        return response;
    }

    @PostMapping("/register")
    public Map<String, String> processRegister(@RequestBody Map<String, Object> body) {
        System.out.println("register");
        User user = new User();
        user.setId(0);
        user.setEmail((String) body.get("email"));
        user.setName((String) body.get("name"));
        user.setPassword((String) body.get("password"));
        if (body.containsKey("description")) {
            user.setDescription((String) body.get("description"));
        } else {
            user.setDescription("");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        try {
            userService.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Failed Register");
        }

        Map<String, String> response = new HashMap<>();
        response.put("msg", "Successfully Registered!");
        return response;
    }

    @RequestMapping("/logout")
    public Map<String, String> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        Map<String, String> res = new HashMap<>();
        res.put("msg", "Successfully logout!");
        return res;
    }
}
