package com.timmax.realestate.web.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.timmax.realestate.model.User;

import static com.timmax.realestate.web.SecurityUtil.authUserId;

@RestController
@RequestMapping(
        value = ProfileRestController.REST_URL,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ProfileRestController extends AbstractUserController {
    public static final String REST_URL = "/rest/profile";

    /* Этот класс не для доступа админа, а для зарегистрированного пользователя, поэтому:
    - нельзя в методы передавать id пользователя,
    - и вместо этого применяется authUserId().
    */
    @GetMapping
    public User get() {
        return super.get(authUserId());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        super.delete(authUserId());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user) {
        super.update(user, authUserId());
    }
}
