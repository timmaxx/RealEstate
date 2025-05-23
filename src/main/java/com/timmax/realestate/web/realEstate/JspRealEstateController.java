package com.timmax.realestate.web.realEstate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.timmax.realestate.model.RealEstate;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static com.timmax.realestate.util.Util.parseFloatOrNull;

@Controller
@RequestMapping("/realEstates")
public class JspRealEstateController extends AbstractRealEstateController {
    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        super.delete(getId(request));
        return "redirect:/realEstates";
    }

    @GetMapping("/update")
    public String update(HttpServletRequest request, Model model) {
        model.addAttribute("realEstate", super.get(getId(request)));
        return "realEstateForm";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute(
                "realEstate",
                new RealEstate("", 1));
        return "realEstateForm";
    }

    @PostMapping
    public String updateOrCreate(HttpServletRequest request) {
        RealEstate realEstate = new RealEstate(
                request.getParameter("address"),
                Float.parseFloat(request.getParameter("square"))
        );

        if (request.getParameter("id").isEmpty()) {
            super.create(realEstate);
        } else {
            super.update(realEstate, getId(request));
        }
        return "redirect:/realEstates";
    }

    @GetMapping("/filter")
    public String getBetween(HttpServletRequest request, Model model) {
        Float startSquare = parseFloatOrNull(request.getParameter("startSquare"));
        Float endSquare = parseFloatOrNull(request.getParameter("endSquare"));
        model.addAttribute(
                "realEstates",
                super.getBetween(startSquare, endSquare)
        );
        return "realEstates";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
