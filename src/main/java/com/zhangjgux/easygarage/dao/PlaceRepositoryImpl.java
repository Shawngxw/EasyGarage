package com.zhangjgux.easygarage.dao;

import com.zhangjgux.easygarage.entity.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

@Repository
public class PlaceRepositoryImpl implements PlaceRepository {

    private EntityManager entityManager;

    @Autowired
    public PlaceRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Place> findAll() {
        TypedQuery<Place> theQuery =
                entityManager.createQuery("SELECT p FROM Place p", Place.class);
        return theQuery.getResultList();
    }

    @Override
    public Place findById(int id) {
        TypedQuery<Place> theQuery =
                entityManager.createQuery("SELECT p FROM Place p WHERE p.id = :id", Place.class);
        theQuery.setParameter("id", id);
        try {
            return theQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Place findByPosition(int floor, int number) {
        TypedQuery<Place> theQuery =
                entityManager.createQuery("SELECT p FROM Place p WHERE p.floor = :floor AND p.number = :number", Place.class);
        theQuery.setParameter("floor", floor);
        theQuery.setParameter("number", number);
        try {
            return theQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Place> findAllFree() {
        TypedQuery<Place> theQuery =
                entityManager.createQuery("SELECT p FROM Place p WHERE p.status = 1", Place.class);
        return theQuery.getResultList();
    }

    @Override
    public List<Place> findFree(int floor, int type) {
        TypedQuery<Place> theQuery;
        if (type == 1) {
            theQuery =
                    entityManager.createQuery("SELECT p FROM Place p WHERE p.floor = :floor AND p.number <= 50 AND p.status = 1", Place.class);
        } else if (type == 2) {
            theQuery =
                    entityManager.createQuery("SELECT p FROM Place p WHERE p.floor = :floor AND p.number <= 80 AND p.number > 50 AND p.status = 1", Place.class);
        } else {
            theQuery =
                    entityManager.createQuery("SELECT p FROM Place p WHERE p.floor = :floor AND p.number > 80 AND p.status = 1", Place.class);
        }
        theQuery.setParameter("floor", floor);
        return theQuery.getResultList();
    }

    @Override
    public void save(Map<String, Object> body) {
        Place place = findByPosition((int) body.get("floor"), (int) body.get(("number")));
        if (place == null) {
            return;
        }
        place.setStatus((int) body.get("status"));
        entityManager.merge(place);
    }

    @Override
    public void deleteById(int id) {
        Place place = findById(id);
        if (place != null) entityManager.remove(place);
    }
}
