package com.zhangjgux.easygarage.service;

import com.zhangjgux.easygarage.dao.CommentRepository;
import com.zhangjgux.easygarage.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    @Transactional
    public Comment findById(int id) {
        return commentRepository.findById(id);
    }

    @Override
    @Transactional
    public Comment findByTime(Timestamp createdAt) {
        return commentRepository.findByTime(createdAt);
    }

    @Override
    @Transactional
    public void save(Map<String, Object> body) {
        commentRepository.save(body);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        commentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByTime(Timestamp createdAt) {
        commentRepository.deleteByTime(createdAt);
    }
}
