package com.zhangjgux.easygarage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "place")
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "status")
    private int status;

    @Column(name = "floor")
    private int floor;

    @Column(name = "number")
    private int number;

    @Column(name = "normal_price")
    private double normalPrice;

    @Column(name = "late_price")
    private double latePrice;

    @JsonIgnore
    @OneToMany(mappedBy = "placeID",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Parking> parkings;

    public Place() {
    }

    public Place(int status, int floor, int number, double normalPrice, double latePrice) {
        this.status = status;
        this.floor = floor;
        this.number = number;
        this.normalPrice = normalPrice;
        this.latePrice = latePrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(double normalPrice) {
        this.normalPrice = normalPrice;
    }

    public double getLatePrice() {
        return latePrice;
    }

    public void setLatePrice(double latePrice) {
        this.latePrice = latePrice;
    }

    public List<Parking> getParkings() {
        return parkings;
    }

    public void setParkings(List<Parking> parkings) {
        this.parkings = parkings;
    }

    @Override
    public String toString() {
        return "Place{" +
                "status=" + status +
                ", floor=" + floor +
                ", number=" + number +
                ", normalPrice=" + normalPrice +
                ", latePrice=" + latePrice +
                '}';
    }
}
