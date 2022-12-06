package com.zhangjgux.easygarage.service;

import com.zhangjgux.easygarage.dao.ReservationRepository;
import com.zhangjgux.easygarage.entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class ReservationServiceImpl implements ReservationService {

    private ReservationRepository reservationRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    @Transactional
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    @Transactional
    public Reservation findById(int id) {
        return reservationRepository.findById(id);
    }

    @Override
    @Transactional
    public Reservation findByTime(Timestamp createdAt) {
        return reservationRepository.findByTime(createdAt);
    }

    @Override
    @Transactional
    public void save(Map<String, Object> body) {
        reservationRepository.save(body);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        reservationRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByTime(Timestamp createdAt) {
        reservationRepository.deleteByTime(createdAt);
    }
}
