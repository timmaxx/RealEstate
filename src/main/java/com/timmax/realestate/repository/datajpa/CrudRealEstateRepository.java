package com.timmax.realestate.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import com.timmax.realestate.model.RealEstate;

public interface CrudRealEstateRepository extends JpaRepository<RealEstate, Integer> {
}
