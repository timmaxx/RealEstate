package com.timmax.realestate.repository.datajpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.timmax.realestate.model.User;

@Transactional(readOnly = true)
public interface CrudUserRepository extends JpaRepository<User, Integer> {
    @Transactional
    @Modifying
    @Query("""
        DELETE
          FROM User u
         WHERE u.id = :id
    """)
    int delete(@Param("id") int id);

    User getByEmail(String email);

    @EntityGraph(attributePaths = {"realEstates", "roles"})
    @Query("""
        SELECT u
          FROM User u
         WHERE u.id = ?1
    """)
    User getWithRealEstates(int id);
}
