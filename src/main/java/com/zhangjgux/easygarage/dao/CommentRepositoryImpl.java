package com.zhangjgux.easygarage.dao;

import com.zhangjgux.easygarage.entity.Comment;
import com.zhangjgux.easygarage.service.ParkingService;
import com.zhangjgux.easygarage.service.UserService;
import com.zhangjgux.easygarage.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Repository
public class CommentRepositoryImpl implements CommentRepository{

    private EntityManager entityManager;
    private UserService userService;
    private ParkingService parkingService;

    @Autowired
    public CommentRepositoryImpl(EntityManager entityManager, UserService userService, ParkingService parkingService) {
        this.entityManager = entityManager;
        this.userService = userService;
        this.parkingService = parkingService;
    }

    @Override
    public List<Comment> findAll() {
        TypedQuery<Comment> theQuery =
                entityManager.createQuery("SELECT c FROM Comment c WHERE c.userID = :userID", Comment.class);
        theQuery.setParameter("userID", userService.getCurrent());
        return theQuery.getResultList();
    }

    @Override
    public Comment findById(int id) {
        TypedQuery<Comment> theQuery =
                entityManager.createQuery("SELECT c FROM Comment c WHERE c.id = :id AND c.userID = :userID", Comment.class);
        theQuery.setParameter("id", id);
        theQuery.setParameter("userID", userService.getCurrent());
        try {
            return theQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Comment findByTime(Timestamp createdAt) {
        List<Comment> cms = findAll();
        for (Comment cm : cms) {
            Timestamp tp = cm.getCreatedAt();
            if (TimeUtils.timeEqual(createdAt, tp)) return cm;
        }
        return null;
    }

    @Override
    public void save(Map<String, Object> body) {
        String text = (String) body.get("text");
        Comment c;
        if (body.containsKey("create")) {
            c = new Comment();
            c.setId(0);
            c.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            c.setParkingID(parkingService.findById((int) body.get("parking_id")));
            c.setUserID(userService.getCurrent());
        } else {
            c = findByTime(TimeUtils.timeToTimestamp((String) body.get("created_at")));
            if (c == null) return;
            c.setText(text);
            c.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        }
        entityManager.merge(c);
    }

    @Override
    public void deleteById(int id) {
        Comment comment = findById(id);
        if (comment != null) entityManager.remove(comment);
    }

    @Override
    public void deleteByTime(Timestamp createdAt) {
        Comment comment = findByTime(createdAt);
        if (comment != null) entityManager.remove(comment);
    }
}
