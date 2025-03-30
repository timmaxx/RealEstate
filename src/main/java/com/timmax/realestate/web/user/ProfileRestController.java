package com.timmax.realestate.web.user;

import com.timmax.realestate.service.UserService;
import com.timmax.realestate.model.User;

import static com.timmax.realestate.web.SecurityUtil.authUserId;

public class ProfileRestController extends AbstractUserController {
    public ProfileRestController(UserService service) {
        super(service);
    }

    public User get() {
        return super.get(authUserId());
    }

    public void delete() {
        super.delete(authUserId());
    }

    public void update(User user) {
        super.update(user, authUserId());
    }
}
