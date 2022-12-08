package com.zhangjgux.easygarage.controller;

import com.zhangjgux.easygarage.entity.Place;
import com.zhangjgux.easygarage.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/places")
public class PlaceController {

    private PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("")
    public List<Place> findAllPlaces() {
        return placeService.findAll();
    }

    @GetMapping("/id/{id}")
    public Place findPlaceById(@PathVariable int id) {
        return placeService.findById(id);
    }

    @GetMapping("/floors")
    public Map<Integer, List<Place>> findPlacesByFloor() {
        return placeService.findAllByFloor();
    }

    @PostMapping("/position")
    public Place findPlaceByPosition(@RequestBody Map<String, Object> body) {
        int floor = (int) body.get("floor");
        int number = (int) body.get("number");
        if (floor < 0 || floor > 3 || number <= 0 || number > 100) {
            throw new RuntimeException("Invalid floor or number!");
        }
        return placeService.findByPosition(floor, number);
    }

    @GetMapping("/free/all")
    public List<Place> findAllFreePlaces() {
        return placeService.findAllFree();
    }

    @PostMapping("/free/param")
    public List<Place> findFreePlaces(@RequestBody Map<String, Object> body) {
        int floor = (int) body.get("floor");
        int type = (int) body.get("type");
        if (floor < 0 || floor > 3 || type < 0 || type > 3) {
            throw new RuntimeException("Invalid floor or type!");
        }
        return placeService.findFree(floor, type);
    }

    @PutMapping("/update")
    public Place updatePlace(@RequestBody Map<String, Object> body) {
        placeService.save(body);
        return placeService.findByPosition((int) body.get("floor"), (int) body.get(("number")));
    }
}
