package com.timmax.realestate.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.timmax.realestate.model.Role;
import com.timmax.realestate.model.User;
import com.timmax.realestate.util.exception.NotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static com.timmax.realestate.UserTestData.*;

public abstract class AbstractUserServiceTest extends AbstractServiceTest {

    @Autowired
    protected UserService service;

    @Test
    void create() {
        User created = service.create(getNew());
        int newId = created.id();
        User newUser = getNew();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(service.get(newId), newUser);
    }

    @Test
    void duplicateMailCreate() {
        assertThrows(
                DataAccessException.class,
                () -> service.create(
                        new User(
                                null,
                                "Duplicate",
                                "user@yandex.ru",
                                "newPass",
                                Role.USER
                        )
                )
        );
    }

    @Test
    void delete() {
        service.delete(USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_ID));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    void get() {
        User user = service.get(ADMIN_ID);
        USER_MATCHER.assertMatch(user, admin);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    void getByEmail() {
        User user = service.getByEmail("admin@gmail.com");
        USER_MATCHER.assertMatch(user, admin);
    }

    @Test
    void update() {
        User updated = getUpdated();
        service.update(updated);
        USER_MATCHER.assertMatch(service.get(USER_ID), getUpdated());
    }

    @Test
    void getAll() {
        //  Получим список пользователей фактических
        List<User> all = service.getAll();
        //  Порядок перечисления ожидаемых пользователей должен соответствовать порядку в списке фактических.
        USER_MATCHER.assertMatch(all, admin, guest, user);
    }

    @Test
    void createWithException() {
        //  В коммите "07 07 HW6 jdbc validation" assume стал не нужен,
        //  но если-бы он всё-ещё был нужен, то см. ниже.
        //  (См. об этом 7_4_JUnit5.mp4 с 5:15)
        // Для JUnit4:
        //  import org.junit.Assume;
        //  ...
        //  Assume.assumeTrue("Validation not supported (JPA only)", isJpaBased());
        // Для JUnit5:
        //  import org.junit.jupiter.api.Assumptions;
        //  ...
        //  Assumptions.assumeTrue(isJpaBased(), "Validation not supported (JPA only)");
        validateRootCause(() -> service.create(new User(null, "  ", "mail@yandex.ru", "password", Role.USER)));
        validateRootCause(() -> service.create(new User(null, "User", "  ", "password", Role.USER)));
        validateRootCause(() -> service.create(new User(null, "User", "mail@yandex.ru", "  ", Role.USER)));
        validateRootCause(() -> service.create(new User(null, "User", "mailyandexru", "password", true, new Date(), Set.of())));
        validateRootCause(() -> service.create(new User(null, "User", "mail@yandex.ru", "pass", true, new Date(), Set.of())));
    }
}
