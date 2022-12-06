package com.zhangjgux.easygarage.service;

import com.zhangjgux.easygarage.dao.PlaceRepository;
import com.zhangjgux.easygarage.entity.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
public class PlaceServiceImpl implements PlaceService {

    private PlaceRepository placeRepository;

    @Autowired
    public PlaceServiceImpl(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Override
    @Transactional
    public List<Place> findAll() {
        return placeRepository.findAll();
    }

    @Override
    @Transactional
    public Place findById(int id) {
        return placeRepository.findById(id);
    }

    @Override
    @Transactional
    public Place findByPosition(int floor, int number) {
        return placeRepository.findByPosition(floor, number);
    }

    @Override
    @Transactional
    public List<Place> findAllFree() {
        return placeRepository.findAllFree();
    }

    @Override
    @Transactional
    public List<Place> findFree(int floor, int type) {
        return placeRepository.findFree(floor, type);
    }

    @Override
    @Transactional
    public void save(Map<String, Object> body) {
        placeRepository.save(body);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        placeRepository.deleteById(id);
    }
}
