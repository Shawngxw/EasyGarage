package com.zhangjgux.easygarage.service;

import com.zhangjgux.easygarage.entity.Place;

import java.util.List;
import java.util.Map;

public interface PlaceService {

    public List<Place> findAll();

    public Place findById(int id);

    public Map<Integer, List<Place>> findAllByFloor();

    public Place findByPosition(int floor, int number);

    public List<Place> findAllFree();

    public List<Place> findFree(int floor, int type);

    public void save(Map<String, Object> body);

    public void deleteById(int id);
}
