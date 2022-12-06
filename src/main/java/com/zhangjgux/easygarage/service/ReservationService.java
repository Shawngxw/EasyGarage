package com.zhangjgux.easygarage.service;

import com.zhangjgux.easygarage.entity.Reservation;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface ReservationService {

    public List<Reservation> findAll();
    public Reservation findById(int id);
    public Reservation findByTime(Timestamp createdAt);
    public void save(Map<String, Object> body);
    public void deleteById(int id);
    public void deleteByTime(Timestamp createdAt);
}
