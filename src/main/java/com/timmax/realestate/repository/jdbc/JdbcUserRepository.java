package com.timmax.realestate.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.timmax.realestate.model.Role;
import com.timmax.realestate.model.User;
import com.timmax.realestate.repository.UserRepository;
import com.timmax.realestate.util.ValidationUtil;

import java.util.*;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        ValidationUtil.validate(user);
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            insertRoles(user);
        } else {
            if (namedParameterJdbcTemplate.update("""
                            UPDATE  users
                               SET  name = :name,
                                    email = :email,
                                    password = :password,
                                    registered = :registered,
                                    enabled = :enabled
                             WHERE id = :id
                            """,
                    parameterSource
            ) == 0) {
                return null;
            }
            // Simplest implementation.
            // More complicated : get user roles from DB and compare them with user.roles (assume that roles are changed rarely).
            // If roles are changed, calculate difference in java and delete/insert them.
            deleteRoles(user);
            insertRoles(user);
        }
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("""
                        DELETE FROM users
                         WHERE id = ?
                        """,
                id
        ) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("""
                        SELECT *
                          FROM users
                         WHERE id = ?
                        """,
                ROW_MAPPER, id
        );
        return setRoles(DataAccessUtils.singleResult(users));
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = jdbcTemplate.query("""
                        SELECT *
                          FROM users
                         WHERE email = ?
                        """,
                ROW_MAPPER, email
        );
        return setRoles(DataAccessUtils.singleResult(users));
    }

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("""
                        SELECT *
                          FROM users
                         ORDER BY name, email
                        """,
                ROW_MAPPER
        );
        Map<Integer, Set<Role>> map = new HashMap<>();
        jdbcTemplate.query("""
                        SELECT *
                          FROM user_role
                        """,
                rs -> {
                    map
                            .computeIfAbsent(rs.getInt("user_id"), userId -> EnumSet.noneOf(Role.class))
                            .add(Role.valueOf(rs.getString("role")));
                });
        users.forEach(u -> u.setRoles(map.get(u.getId())));

        return users;
    }

    private void insertRoles(User u) {
        Set<Role> roles = u.getRoles();
        if (!CollectionUtils.isEmpty(roles)) {
            jdbcTemplate.batchUpdate("""
                            INSERT
                              INTO user_role
                                   (user_id, role)
                            VALUES (?, ?)
                            """,
                    roles, roles.size(),
                    (ps, role) -> {
                        ps.setInt(1, u.id());
                        ps.setString(2, role.name());
                    }
            );
        }
    }

    private void deleteRoles(User u) {
        jdbcTemplate.update("""
                        DELETE
                          FROM user_role
                         WHERE user_id = ?
                        """,
                u.getId()
        );
    }

    private User setRoles(User u) {
        if (u != null) {
            List<Role> roles = jdbcTemplate.queryForList("""
                            SELECT role
                              FROM user_role
                             WHERE user_id = ?
                            """,
                    Role.class, u.getId()
            );
            u.setRoles(roles);
        }
        return u;
    }
}
