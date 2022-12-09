package com.zhangjgux.easygarage.controller;

import com.zhangjgux.easygarage.entity.Vehicle;
import com.zhangjgux.easygarage.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("")
    public List<Vehicle> findAllVehicles() {
        return vehicleService.findAll();
    }

    @GetMapping("/id/{id}")
    public Vehicle findVehicleById(@PathVariable int id) {
        Vehicle res = vehicleService.findById(id);
        if (res == null) throw new RuntimeException("Vehicle not found");
        return res;
    }

    @GetMapping("/name/{name}")
    public Vehicle findVehicleByName(@PathVariable String name) {
        Vehicle res = vehicleService.findByName(name);
        if (res == null) throw new RuntimeException("Vehicle not found");
        return res;
    }

    @GetMapping("/type/{type}")
    public List<Vehicle> findVehicleByType(@PathVariable int type) {
        return vehicleService.findByType(type);
    }

    @GetMapping("/place/{id}")
    public Vehicle findVehicleByPlace(@PathVariable int id) {
        return vehicleService.findByPlaceId(id);
    }

    @PostMapping("/add")
    public Vehicle addNewVehicle(@RequestBody Map<String, Object> body) {
        String name = (String) body.get("name");
        if (vehicleService.findByName(name) != null) return null;
        vehicleService.save(body);
        return vehicleService.findByName(name);
    }

    @PutMapping("/update")
    public Vehicle updateVehicle(@RequestBody Map<String, Object> body) {
        String name = (String) body.get("name");
        if (vehicleService.findByName(name) == null) return null;
        vehicleService.save(body);
        return vehicleService.findByName(name);
    }

    @DeleteMapping("/name_delete/{name}")
    public Map<String, String> deleteVehicleByName(@PathVariable String name) {
        if (vehicleService.findByName(name) == null) throw new RuntimeException("No vehicle to delete");
        vehicleService.deleteByName(name);
        Map<String, String> res = new HashMap<>();
        res.put("msg", "Successfully Deleted!");
        return res;
    }

    @DeleteMapping("/id_delete/{id}")
    public Map<String, String> deleteVehicleById(@PathVariable int id) {
        if (vehicleService.findById(id) == null) throw new RuntimeException("No vehicle to delete");
        vehicleService.deleteById(id);
        Map<String, String> res = new HashMap<>();
        res.put("msg", "Successfully Deleted!");
        return res;
    }
}
