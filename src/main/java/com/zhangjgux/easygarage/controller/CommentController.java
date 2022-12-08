package com.zhangjgux.easygarage.controller;

import com.zhangjgux.easygarage.entity.Comment;
import com.zhangjgux.easygarage.service.CommentService;
import com.zhangjgux.easygarage.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("")
    public List<Comment> findAll() {
        return commentService.findAll();
    }

    @GetMapping("/id/{id}")
    public Comment findById(@PathVariable int id) {
        return commentService.findById(id);
    }

    @PostMapping("/time")
    public Comment findByTime(@RequestBody Map<String, Object> body) {
        Timestamp createdAt = TimeUtils.timeToTimestamp((String) body.get("created_at"));
        return commentService.findByTime(createdAt);
    }

    @PostMapping("/add")
    public Comment add(@RequestBody  Map<String, Object> body) {
        commentService.save(body);
        List<Comment> comments = commentService.findAll();
        return comments.get(comments.size() - 1);
    }

    @PutMapping("/update")
    public Comment update(@RequestBody  Map<String, Object> body) {
        commentService.save(body);
        return commentService.findById((int) body.get("id"));
    }

    @DeleteMapping("/delete/id/{id}")
    public String deleteById(@PathVariable int id) {
        Comment c = commentService.findById(id);
        if (c == null) return "Comment Not Found!";
        commentService.deleteById(id);
        return "Successfully Deleted!";
    }

    @DeleteMapping("/delete/time/{time}")
    public String deleteByTime(@PathVariable String time) {
        Timestamp createdAt = TimeUtils.timeToTimestamp(time);
        Comment c = commentService.findByTime(createdAt);
        if (c == null) return "Comment Not Found!";
        commentService.deleteByTime(createdAt);
        return "Successfully Deleted!";
    }
}
