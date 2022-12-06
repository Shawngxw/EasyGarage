package com.zhangjgux.easygarage.service;

import com.zhangjgux.easygarage.entity.User;

import java.util.List;

public interface UserService {
    public User getUserByEmail(String email);

    public User getCurrent();

    public List<User> findAll();

    public User findById(int id);

    public void save(User user);

    public void deleteById(int id);
}
