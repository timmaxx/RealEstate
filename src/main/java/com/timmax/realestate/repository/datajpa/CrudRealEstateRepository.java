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
    //  TimMax:
    //      Во всех запросах есть полностью и частично повторяющиеся блоки.
    //      А что если эти повторы вынести в отдельные строковые константы и собирать из них нужные запросы?!
    String delete = """
                DELETE
            """;
    String select = """
                SELECT re
            """;

    String from = """
                  FROM RealEstate re
            """;

    String where = """
                 WHERE re.user.id = :userId
            """;

    String orderBy = """
                ORDER BY re.address
            """;

    @Modifying
    @Transactional
    @Query(delete +
            from +
            where + """
                  AND re.id = :id
            """)
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Query(select +
            from +
            where +
            orderBy)
    List<RealEstate> getAll(@Param("userId") int userId);

    @Query(select +
            from +
            where + """
                   AND re.square >= :startSquare
                   AND re.square < :endSquare
            """ + orderBy
    )
    List<RealEstate> getBetweenHalfOpen(
            @Param("startSquare") Float startSquare,
            @Param("endSquare") Float endSquare,
            @Param("userId") int userId
    );
}
