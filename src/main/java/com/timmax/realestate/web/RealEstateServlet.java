package com.timmax.realestate.web;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;
import com.timmax.realestate.Profiles;
import com.timmax.realestate.model.RealEstate;
import com.timmax.realestate.web.realEstate.RealEstateRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.timmax.realestate.util.Util.parseFloatOrNull;

public class RealEstateServlet extends HttpServlet {

    private ClassPathXmlApplicationContext springContext;
    private RealEstateRestController realEstateController;

    @Override
    public void init() {
        springContext = new ClassPathXmlApplicationContext(new String[]{"spring/spring-app.xml", "spring/spring-db.xml"}, false);
//        springContext.setConfigLocations("spring/spring-app.xml", "spring/spring-db.xml");
        springContext.getEnvironment().setActiveProfiles(Profiles.getActiveDbProfile(), Profiles.REPOSITORY_IMPLEMENTATION);
        springContext.refresh();
        realEstateController = springContext.getBean(RealEstateRestController.class);
    }

    @Override
    public void destroy() {
        springContext.close();
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        RealEstate realEstate = new RealEstate(
                request.getParameter("address"),
                Float.parseFloat(request.getParameter("square"))
        );

        if (StringUtils.hasLength(request.getParameter("id"))) {
            realEstateController.update(realEstate, getId(request));
        } else {
            realEstateController.create(realEstate);
        }
        response.sendRedirect("realEstates");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete" -> {
                int id = getId(request);
                realEstateController.delete(id);
                response.sendRedirect("realEstates");
            }
            case "create", "update" -> {
                final RealEstate realEstate =
                        "create".equals(action) ?
                                new RealEstate("", 1) :
                                realEstateController.get(getId(request));
                request.setAttribute("realEstate", realEstate);
                request.getRequestDispatcher("/realEstateForm.jsp").forward(request, response);
            }
            case "filter" -> {
                Float startSquare = parseFloatOrNull(request.getParameter("startSquare"));
                Float endSquare = parseFloatOrNull(request.getParameter("endSquare"));
                request.setAttribute("realEstates", realEstateController.getBetween(startSquare, endSquare));
                request.getRequestDispatcher("/realEstates.jsp").forward(request, response);
            }
            default -> {
                request.setAttribute(
                        "realEstates",
                        realEstateController.getAll()
                );
                request.getRequestDispatcher("/realEstates.jsp").forward(request, response);
            }
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
