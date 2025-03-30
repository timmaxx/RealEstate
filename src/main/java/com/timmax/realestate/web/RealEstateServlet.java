package com.timmax.realestate.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.timmax.realestate.model.RealEstate;
import com.timmax.realestate.repository.inmemory.InMemoryRealEstateRepository;
import com.timmax.realestate.repository.RealEstateRepository;
import com.timmax.realestate.util.RealEstateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class RealEstateServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(RealEstateServlet.class);

    private RealEstateRepository repository;

    @Override
    public void init() {
        repository = new InMemoryRealEstateRepository();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        RealEstate realEstate = new RealEstate(
                id.isEmpty() ? null : Integer.valueOf(id),
                request.getParameter("address"),
                Float.parseFloat(request.getParameter("square"))
        );

        log.info(realEstate.isNew() ? "Create {}" : "Update {}", realEstate);
        repository.save(realEstate);
        response.sendRedirect("realEstates");
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete id={}", id);
                repository.delete(id);
                response.sendRedirect("realEstates");
                break;
            case "create":
            case "update":
                final RealEstate realEstate =
                        "create".equals(action) ?
                                new RealEstate("", 1) :
                                repository.get(getId(request));
                request.setAttribute("realEstate", realEstate);
                request.getRequestDispatcher("/realEstateForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute(
                        "realEstates",
                        RealEstateUtil.getDtos(repository.getAll())
                );
                request.getRequestDispatcher("/realEstates.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
