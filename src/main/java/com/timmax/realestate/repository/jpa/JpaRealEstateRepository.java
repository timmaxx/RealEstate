package com.timmax.realestate.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.timmax.realestate.model.RealEstate;
import com.timmax.realestate.model.User;
import com.timmax.realestate.repository.RealEstateRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaRealEstateRepository implements RealEstateRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public RealEstate save(RealEstate realEstate, int userId) {
        realEstate.setUser(em.getReference(User.class, userId));
        if (realEstate.isNew()) {
            em.persist(realEstate);
            return realEstate;
        }
        return get(realEstate.id(), userId) == null ? null : em.merge(realEstate);
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(RealEstate.DELETE_BY_ID)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public RealEstate get(int id, int userId) {
        RealEstate realEstate = em.find(RealEstate.class, id);
        return realEstate != null && realEstate.getUser().getId() == userId ? realEstate : null;
    }

    @Override
    public List<RealEstate> getAll(int userId) {
        return em.createNamedQuery(RealEstate.GET_ALL_SORTED_BY_ADDRESS, RealEstate.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<RealEstate> getBetweenHalfOpen(Float startSquare, Float endSquare, int userId) {
        return em.createNamedQuery(RealEstate.GET_BETWEEN_SQUARE, RealEstate.class)
                .setParameter("userId", userId)
                .setParameter("startSquare", startSquare)
                .setParameter("endSquare", endSquare)
                .getResultList();
    }
}
