package com.timmax.realestate.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.timmax.realestate.service.RealEstateService;
import com.timmax.realestate.service.UserService;
import com.timmax.realestate.util.RealEstateUtil;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RootController {
    private static final Logger log = LoggerFactory.getLogger(RootController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RealEstateService realEstateService;

    @GetMapping("/")
    public String root() {
        log.info("root");
        return "index";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        log.info("users");
        model.addAttribute("users", userService.getAll());
        return "users";
    }

    @PostMapping("/users")
    public String setUser(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        log.info("setUser {}", userId);
        SecurityUtil.setAuthUserId(userId);
        return "redirect:realEstates";
    }

    @GetMapping("/realEstates")
    public String getRealEstates(Model model) {
        log.info("realEstates");
        model.addAttribute(
                "realEstates",
                RealEstateUtil.getDtos(
                        realEstateService.getAll(
                            SecurityUtil.authUserId()
                        )
                )
        );
        return "realEstates";
    }
}
