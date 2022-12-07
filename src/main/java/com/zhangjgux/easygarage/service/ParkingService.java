package com.zhangjgux.easygarage.service;

import com.zhangjgux.easygarage.entity.Parking;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface ParkingService {
    public List<Parking> findAll();

    public Parking findById(int id);

    public Parking findByTime(Timestamp begin);

    public Parking findReservationById(int id);

    public void save(Map<String, Object> body);

    public void deleteById(int id);

    public void deleteByTime(Timestamp begin);
}
