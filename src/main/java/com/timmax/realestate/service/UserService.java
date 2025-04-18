package com.timmax.realestate.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.timmax.realestate.model.User;
import com.timmax.realestate.repository.UserRepository;

import java.util.List;

import static com.timmax.realestate.util.ValidationUtil.checkNotFound;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(user);
    }

    public void delete(int id) {
        checkNotFound(repository.delete(id), id);
    }

    public User get(int id) {
        return checkNotFound(repository.get(id), id);
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    public List<User> getAll() {
        return repository.getAll();
    }

    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        checkNotFound(repository.save(user), user.getId());
    }
}
