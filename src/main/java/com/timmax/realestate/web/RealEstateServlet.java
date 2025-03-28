package com.timmax.realestate.web;

import com.timmax.realestate.util.RealEstateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RealEstateServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(RealEstateServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("forward to realEstates");
        request.setAttribute("realEstates", RealEstateUtil.getDtos(RealEstateUtil.realEstates));
        request.getRequestDispatcher("/realEstates.jsp").forward(request, response);
    }
}
