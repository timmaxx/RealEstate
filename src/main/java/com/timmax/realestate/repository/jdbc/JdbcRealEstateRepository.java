package com.timmax.realestate.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import com.timmax.realestate.model.RealEstate;
import com.timmax.realestate.repository.RealEstateRepository;

import java.util.List;

@Repository
public class JdbcRealEstateRepository implements RealEstateRepository {
    private static final RowMapper<RealEstate> ROW_MAPPER = BeanPropertyRowMapper.newInstance(RealEstate.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertRealEstate;

    @Autowired
    public JdbcRealEstateRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertRealEstate = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("real_estate")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public RealEstate save(RealEstate realEstate, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", realEstate.getId())
                .addValue("address", realEstate.getAddress())
                .addValue("square", realEstate.getSquare())
                .addValue("user_id", userId);

        if (realEstate.isNew()) {
            Number newId = insertRealEstate.executeAndReturnKey(map);
            realEstate.setId(newId.intValue());
        } else {
            if (namedParameterJdbcTemplate.update("" +
                            "UPDATE real_estate " +
                            "   SET address = :address," +
                            "       square = :square" +
                            " WHERE id = :id" +
                            "   AND user_id = :user_id",
                    map) == 0
            ) {
                return null;
            }
        }
        return realEstate;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("" +
                        "DELETE FROM real_estate" +
                        " WHERE id = ?" +
                        "   AND user_id = ?",
                id, userId
        ) != 0;
    }

    @Override
    public RealEstate get(int id, int userId) {
        List<RealEstate> realEstates = jdbcTemplate.query("" +
                        "SELECT *" +
                        "  FROM real_estate" +
                        " WHERE id = ?" +
                        "   AND user_id = ?",
                ROW_MAPPER, id, userId
        );
        return DataAccessUtils.singleResult(realEstates);

    }

    @Override
    public List<RealEstate> getAll(int userId) {
        return jdbcTemplate.query("" +
                        "SELECT *" +
                        "  FROM real_estate" +
                        " WHERE user_id = ?" +
                        " ORDER BY address",
                ROW_MAPPER, userId
        );
    }

    @Override
    public List<RealEstate> getBetweenHalfOpen(Float startSquare, Float endSquare, int userId) {
        return jdbcTemplate.query("" +
                        "SELECT *" +
                        "  FROM real_estate" +
                        " WHERE user_id = ?" +
                        "   AND square >= ? AND square < ?" +
                        " ORDER BY address",
                ROW_MAPPER, userId, startSquare, endSquare
        );
    }
}
