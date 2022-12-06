package com.zhangjgux.easygarage.dao;

import com.zhangjgux.easygarage.entity.User;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepository {

    public User getUserByEmail(String email);

    public User getCurrent();

    public List<User> findAll();

    public User findById(int id);

    public void save(User user);

    public void deleteById(int id);
}
