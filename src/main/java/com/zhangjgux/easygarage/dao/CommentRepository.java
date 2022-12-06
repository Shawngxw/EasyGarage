package com.zhangjgux.easygarage.dao;

import com.zhangjgux.easygarage.entity.Comment;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface CommentRepository {

    public List<Comment> findAll();
    public Comment findById(int id);
    public Comment findByTime(Timestamp createdAt);
    public void save(Map<String, Object> body);
    public void deleteById(int id);
    public void deleteByTime(Timestamp createdAt);
}
