package com.zhangjgux.easygarage.controller;

import com.zhangjgux.easygarage.entity.Reservation;
import com.zhangjgux.easygarage.service.ReservationService;
import com.zhangjgux.easygarage.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("")
    public List<Reservation> findAllReservations() {
        return reservationService.findAll();
    }

    @GetMapping("/id/{id}")
    public Reservation findReservationById(@PathVariable int id) {
        return reservationService.findById(id);
    }

    @PostMapping("/time")
    public Reservation findReservationByTime(@RequestBody Map<String, Object> body) {
        Timestamp createdAt = TimeUtils.timeToTimestamp((String) body.get("created_at"));
        return reservationService.findByTime(createdAt);
    }

    @PostMapping("/add")
    public Reservation addReservation(@RequestBody Map<String, Object> body) {
        reservationService.save(body);
        List<Reservation> reservations = reservationService.findAll();
        return reservations.get(reservations.size() - 1);
    }

    @PutMapping("/update")
    public Reservation updateReservation(@RequestBody Map<String, Object> body) {
        reservationService.save(body);
        return reservationService.findById((int) body.get("id"));
    }

    @DeleteMapping("/delete/id/{id}")
    public String deleteReservationById(@PathVariable int id) {
        Reservation r = reservationService.findById(id);
        if (r == null) return "Reservation Not Found!";
        reservationService.deleteById(id);
        return "Successfully Deleted!";
    }

    @DeleteMapping("/delete/time/{time}")
    public String deleteReservationByTime(@PathVariable String time) {
        Timestamp createdAt = TimeUtils.timeToTimestamp(time);
        Reservation r = reservationService.findByTime(createdAt);
        if (r == null) return "Reservation Not Found!";
        reservationService.deleteByTime(createdAt);
        return "Successfully Deleted!";
    }
}
