package com.zhangjgux.easygarage.controller;

import com.zhangjgux.easygarage.entity.Parking;
import com.zhangjgux.easygarage.service.ParkingService;
import com.zhangjgux.easygarage.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/parkings")
public class ParkingController {

    private ParkingService parkingService;

    @Autowired
    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping("")
    public List<Parking> findAllParkings() {
        return parkingService.findAll();
    }

    @GetMapping("/id/{id}")
    public Parking findParkingById(@PathVariable int id) {
        return parkingService.findById(id);
    }

    @PostMapping("/time")
    public Parking findParkingByTime(@RequestBody Map<String, Object> body) {
        String readableTime = (String) body.get("begin");
        Timestamp begin = TimeUtils.timeToTimestamp(readableTime);
        return parkingService.findByTime(begin);
    }

    @PostMapping("/add")
    public Parking add(@RequestBody Map<String, Object> body) {
        parkingService.save(body);
        List<Parking> parkings = parkingService.findAll();
        return parkings.get(parkings.size() - 1);
    }

    @PutMapping("/update")
    public Parking update(@RequestBody Map<String, Object> body) {
        parkingService.save(body);
        return parkingService.findById((int) body.get("id"));
    }

    @DeleteMapping("/delete/id/{id}")
    public String deleteById(@PathVariable int id) {
        if (parkingService.findById(id) == null) {
            return "Parking Not Found!";
        }
        parkingService.deleteById(id);
        return "Successfully Deleted!";
    }

    @DeleteMapping("/delete/time/{time}")
    public String deleteByTime(@PathVariable String time) {
        Timestamp begin = TimeUtils.timeToTimestamp(time);
        Parking p = parkingService.findByTime(begin);
        if (p == null) return "Reservation Not Found!";
        parkingService.deleteByTime(begin);
        return "Successfully Deleted!";
    }
}
