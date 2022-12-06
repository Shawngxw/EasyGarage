package com.zhangjgux.easygarage.service;

import com.zhangjgux.easygarage.dao.ParkingRepository;
import com.zhangjgux.easygarage.entity.Parking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class ParkingServiceImpl implements ParkingService {

    private ParkingRepository parkingRepository;

    @Autowired
    public ParkingServiceImpl(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Override
    @Transactional
    public List<Parking> findAll() {
        return parkingRepository.findAll();
    }

    @Override
    @Transactional
    public Parking findById(int id) {
        return parkingRepository.findById(id);
    }

    @Override
    @Transactional
    public Parking findByTime(Timestamp begin) {
        return parkingRepository.findByTime(begin);
    }

    @Override
    @Transactional
    public void save(Map<String, Object> body) {
        parkingRepository.save(body);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        parkingRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByTime(Timestamp begin) {
        parkingRepository.deleteByTime(begin);
    }
}
