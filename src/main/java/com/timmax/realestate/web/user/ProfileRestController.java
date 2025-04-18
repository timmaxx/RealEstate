package com.timmax.realestate.web.user;

import org.springframework.stereotype.Controller;
import com.timmax.realestate.model.User;

import static com.timmax.realestate.web.SecurityUtil.authUserId;

@Controller
public class ProfileRestController extends AbstractUserController {

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
