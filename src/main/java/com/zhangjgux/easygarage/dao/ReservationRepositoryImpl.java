package com.zhangjgux.easygarage.dao;

import com.zhangjgux.easygarage.entity.Parking;
import com.zhangjgux.easygarage.entity.Place;
import com.zhangjgux.easygarage.entity.Reservation;
import com.zhangjgux.easygarage.entity.Vehicle;
import com.zhangjgux.easygarage.service.ParkingService;
import com.zhangjgux.easygarage.service.PlaceService;
import com.zhangjgux.easygarage.service.UserService;
import com.zhangjgux.easygarage.service.VehicleService;
import com.zhangjgux.easygarage.utils.ParkingUtils;
import com.zhangjgux.easygarage.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private EntityManager entityManager;
    private ParkingService parkingService;
    private PlaceService placeService;
    private VehicleService vehicleService;
    private UserService userService;

    private ParkingUtils parkingUtils;

    @Autowired
    public ReservationRepositoryImpl(EntityManager entityManager, ParkingService parkingService,
                                     PlaceService placeService, VehicleService vehicleService,
                                     UserService userService, ParkingUtils parkingUtils) {
        this.entityManager = entityManager;
        this.parkingService = parkingService;
        this.placeService = placeService;
        this.vehicleService = vehicleService;
        this.userService = userService;
        this.parkingUtils = parkingUtils;
    }

    @Override
    public List<Reservation> findAll() {
        TypedQuery<Reservation> theQuery =
                entityManager.createQuery("SELECT r FROM Reservation r WHERE r.userID = :userID", Reservation.class);
        theQuery.setParameter("userID", userService.getCurrent());
        return theQuery.getResultList();
    }

    @Override
    public Reservation findById(int id) {
        TypedQuery<Reservation> theQuery =
                entityManager.createQuery("SELECT r FROM Reservation r WHERE r.id = :id AND r.userID = :userID", Reservation.class);
        theQuery.setParameter("id", id);
        theQuery.setParameter("userID", userService.getCurrent());
        try {
            return theQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Reservation findByTime(Timestamp createdAt) {
        List<Reservation> rss = findAll();
        for (Reservation rs : rss) {
            Timestamp tp = rs.getCreatedAt();
            if (TimeUtils.timeEqual(createdAt, tp)) return rs;
        }
        return null;
    }

    @Override
    public void save(Map<String, Object> body) {
        int status = (int) body.get("status");
        if (status == 1) {
            Reservation r = new Reservation();
            r.setId(0);
            Timestamp stamp = new Timestamp(System.currentTimeMillis());
            r.setCreatedAt(stamp);
            r.setUserID(userService.getCurrent());
            r.setStatus(1);
            entityManager.merge(r);

            Place place = placeService.findByPosition((int) body.get("floor"), (int) body.get("number"));
            place.setStatus(3);

            Parking p = new Parking();
            p.setId(0);
            p.setStatus(3);
            p.setBegin(TimeUtils.timeToTimestamp((String) body.get("begin_time")));
            p.setEnd(TimeUtils.timeToTimestamp((String) body.get("end_time")));
            Vehicle v = vehicleService.findByName((String) body.get("vehicle_name"));
            v.setStatus(2);
            entityManager.merge(v);
            place.setVehicleID(v);
            entityManager.merge(place);
            p.setVehicleID(v);
            p.setUserID(userService.getCurrent());
            p.setPlaceID(placeService.findByPosition((int) body.get("floor"), (int) body.get("number")));
            p.setCost((p.getBegin().getTime() - r.getCreatedAt().getTime()) / 3600000 +
                    parkingUtils.getCost(p.getBegin(), p.getEnd(), place.getNormalPrice(), place.getLatePrice()));
            p.setReservationID(findByTime(stamp));
            entityManager.merge(p);
        } else if (status == 0) {
            Reservation r = findById((int) body.get("id"));
            r.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            Parking p = parkingService.findReservationById(r.getId());
            p.setStatus(2);
            Place place = p.getPlaceID();
            place.setStatus(1);
            place.setVehicleID(null);
            entityManager.merge(place);
            Vehicle v = p.getVehicleID();
            v.setStatus(1);
            entityManager.merge(v);
            r.setStatus(0);
            entityManager.merge(r);
            entityManager.merge(p);
        }
    }

    @Override
    public void deleteById(int id) {
        Reservation reservation = findById(id);
        if (reservation != null) entityManager.remove(reservation);
    }

    @Override
    public void deleteByTime(Timestamp createdAt) {
        Reservation reservation = findByTime(createdAt);
        if (reservation != null) entityManager.remove(reservation);
    }
}
