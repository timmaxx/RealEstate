package com.timmax.realestate.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.timmax.realestate.model.RealEstate;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudRealEstateRepository extends JpaRepository<RealEstate, Integer> {

    @Modifying
    @Transactional
    @Query("""
        DELETE
          FROM RealEstate re
         WHERE re.user.id = :userId
           AND re.id = :id
    """)
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Query("""
        SELECT re
          FROM RealEstate re
         WHERE re.user.id = :userId
         ORDER BY re.address
    """)
    List<RealEstate> getAll(@Param("userId") int userId);

    @Query("""
        SELECT re
          FROM RealEstate re
         WHERE re.user.id = :userId
           AND re.square >= :startSquare
           AND re.square < :endSquare
         ORDER BY re.address
    """)
    List<RealEstate> getBetweenHalfOpen(
            @Param("startSquare") Float startSquare,
            @Param("endSquare") Float endSquare,
            @Param("userId") int userId
    );
}
