package com.zhangjgux.easygarage.dao;

import com.zhangjgux.easygarage.entity.Place;
import com.zhangjgux.easygarage.entity.User;
import com.zhangjgux.easygarage.entity.Vehicle;
import com.zhangjgux.easygarage.service.PlaceService;
import com.zhangjgux.easygarage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

@Repository
public class VehicleRepositoryImpl implements VehicleRepository {

    private EntityManager entityManager;
    private UserService userService;
    private PlaceService placeService;

    @Autowired
    public VehicleRepositoryImpl(EntityManager entityManager, UserService userService, PlaceService placeService) {
        this.entityManager = entityManager;
        this.userService = userService;
        this.placeService = placeService;
    }

    @Override
    public List<Vehicle> findAll() {
        User me = userService.getCurrent();
        TypedQuery<Vehicle> theQuery =
                entityManager.createQuery("SELECT v FROM Vehicle v WHERE v.userID = :user", Vehicle.class);
        theQuery.setParameter("user", me);
        return theQuery.getResultList();
    }

    @Override
    public Vehicle findById(int id) {
        User me = userService.getCurrent();
        TypedQuery<Vehicle> theQuery =
                entityManager.createQuery("SELECT v FROM Vehicle v WHERE v.userID = :user AND v.id = :id", Vehicle.class);
        theQuery.setParameter("user", me);
        theQuery.setParameter("id", id);
        try {
            return theQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Vehicle findByName(String name) {
        User me = userService.getCurrent();
        TypedQuery<Vehicle> theQuery =
                entityManager.createQuery("SELECT v FROM Vehicle v WHERE v.userID = :user AND v.name = :name", Vehicle.class);
        theQuery.setParameter("user", me);
        theQuery.setParameter("name", name);
        try {
            return theQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

    @Override
    public List<Vehicle> findByType(int type) {
        User me = userService.getCurrent();
        TypedQuery<Vehicle> theQuery =
                entityManager.createQuery("SELECT v FROM Vehicle v WHERE v.userID = :user AND v.type = :type", Vehicle.class);
        theQuery.setParameter("user", me);
        theQuery.setParameter("type", type);
        return theQuery.getResultList();
    }

    @Override
    public Vehicle findByPlaceId(int id) {
        User me = userService.getCurrent();
        Place place = placeService.findById(id);
        return place.getVehicleID();
    }

    @Override
    public void save(Map<String, Object> body) {
        User me = userService.getCurrent();
        Vehicle v = null;
        for (Vehicle veh : me.getVehicles()) {
            if (veh.getName().equals((String) body.get("name"))) {
                v = veh;
            }
        }
        if (v == null) {
            v = new Vehicle();
            v.setId(0);
        }
        v.setName((String) body.get("name"));
        v.setType((int) body.get("type"));
        v.setStatus((int) body.get("status"));
        v.setUserID(userService.getCurrent());
        Vehicle v2 = entityManager.merge(v);
        v.setId(v2.getId());
    }

    @Override
    public void deleteByName(String name) {
        User me = userService.getCurrent();
        TypedQuery<Vehicle> theQuery =
                entityManager.createQuery("SELECT v FROM Vehicle v WHERE v.userID = :user AND v.name = :name", Vehicle.class);
        theQuery.setParameter("user", me);
        theQuery.setParameter("name", name);
        entityManager.remove(theQuery.getSingleResult());
    }

    @Override
    public void deleteById(int id) {
        TypedQuery<Vehicle> theQuery =
                entityManager.createQuery("SELECT v FROM Vehicle v WHERE v.id = :id", Vehicle.class);
        theQuery.setParameter("id", id);
        entityManager.remove(theQuery.getSingleResult());
    }
}
