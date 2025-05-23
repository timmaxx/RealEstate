package com.timmax.realestate.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.timmax.realestate.model.User;
import com.timmax.realestate.repository.UserRepository;

import java.util.List;

import static com.timmax.realestate.util.ValidationUtil.checkNotFound;

@Service
public class UserService {
    private final String MUST_NOT_BE_NULL = " must not be null";
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "users", allEntries = true)
    public User create(User user) {
        Assert.notNull(user, "user" + MUST_NOT_BE_NULL);
        return repository.save(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) {
        checkNotFound(repository.delete(id), id);
    }

    public User get(int id) {
        return checkNotFound(repository.get(id), id);
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "email" + MUST_NOT_BE_NULL);
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    @Cacheable("users")
    public List<User> getAll() {
        return repository.getAll();
    }

    @CacheEvict(value = "users", allEntries = true)
    public void update(User user) {
        Assert.notNull(user, "user" + MUST_NOT_BE_NULL);
        checkNotFound(repository.save(user), user.getId());
    }

    public User getWithRealEstates(int id) {
        return checkNotFound(repository.getWithRealEstates(id), id);
    }
}
