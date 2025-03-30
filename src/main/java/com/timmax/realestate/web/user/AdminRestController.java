package com.timmax.realestate.web.user;

import com.timmax.realestate.service.UserService;
import com.timmax.realestate.model.User;

import java.util.List;

public class AdminRestController extends AbstractUserController {
    public AdminRestController(UserService service) {
        super(service);
    }

    @Override
    public List<User> getAll() {
        return super.getAll();
    }

    @Override
    public User get(int id) {
        return super.get(id);
    }

    @Override
    public User create(User user) {
        return super.create(user);
    }

    @Override
    public void delete(int id) {
        super.delete(id);
    }

    @Override
    public void update(User user, int id) {
        super.update(user, id);
    }

    @Override
    public User getByMail(String email) {
        return super.getByMail(email);
    }
}
