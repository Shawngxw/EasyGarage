package com.zhangjgux.easygarage.service;

import com.zhangjgux.easygarage.dao.VehicleRepository;
import com.zhangjgux.easygarage.entity.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
public class VehicleServiceImpl implements VehicleService{

    private VehicleRepository vehicleRepository;

    @Autowired
    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    @Transactional
    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    @Override
    @Transactional
    public Vehicle findById(int id) {
        return vehicleRepository.findById(id);
    }

    @Override
    @Transactional
    public Vehicle findByName(String name) {
        return vehicleRepository.findByName(name);
    }

    @Override
    @Transactional
    public List<Vehicle> findByType(int type) {
        return vehicleRepository.findByType(type);
    }

    @Override
    @Transactional
    public void save(Map<String, Object> body) {
        vehicleRepository.save(body);
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        vehicleRepository.deleteByName(name);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        vehicleRepository.deleteById(id);
    }
}
