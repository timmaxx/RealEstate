package com.timmax.realestate.repository.datajpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.timmax.realestate.model.RealEstate;
import com.timmax.realestate.repository.RealEstateRepository;

import java.util.List;

@Repository
public class DataJpaRealEstateRepository implements RealEstateRepository {

    private final CrudRealEstateRepository crudRealEstateRepository;
    private final CrudUserRepository crudUserRepository;

    public DataJpaRealEstateRepository(
            CrudRealEstateRepository crudRealEstateRepository,
            CrudUserRepository crudUserRepository) {
        this.crudRealEstateRepository = crudRealEstateRepository;
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    @Transactional
    public RealEstate save(RealEstate realEstate, int userId) {
        if (!realEstate.isNew() && get(realEstate.id(), userId) == null) {
            return null;
        }
        realEstate.setUser(crudUserRepository.getReferenceById(userId));
        return crudRealEstateRepository.save(realEstate);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRealEstateRepository.delete(id, userId) != 0;
    }

    @Override
    public RealEstate get(int id, int userId) {
        return crudRealEstateRepository.findById(id)
                .filter(realEstate -> realEstate.getUser().getId() == userId)
                .orElse(null);
    }

    @Override
    public List<RealEstate> getAll(int userId) {
        return crudRealEstateRepository.getAll(userId);
    }

    @Override
    public List<RealEstate> getBetweenHalfOpen(Float startSquare, Float endSquare, int userId) {
        return crudRealEstateRepository.getBetweenHalfOpen(startSquare, endSquare, userId);
    }

    @Override
    public RealEstate getWithUser(int id, int userId) {
        return crudRealEstateRepository.getWithUser(id, userId);
    }
}
