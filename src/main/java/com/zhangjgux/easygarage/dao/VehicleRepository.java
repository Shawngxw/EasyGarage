package com.zhangjgux.easygarage.dao;

import com.zhangjgux.easygarage.entity.Vehicle;

import java.util.List;
import java.util.Map;

public interface VehicleRepository {
    public List<Vehicle> findAll();
    public Vehicle findById(int id);
    public Vehicle findByName(String name);
    public List<Vehicle> findByType(int type);

    public Vehicle findByPlaceId(int id);
    public void save(Map<String, Object> body);

    public void deleteByName(String name);
    public void deleteById(int id);
}
