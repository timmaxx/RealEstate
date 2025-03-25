package com.timmax.realestate.web;

import org.slf4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //  request.getRequestDispatcher("/users.jsp").forward(request, response);

        //  В файле logback.xml указан уровень логирования debug.
        //  <logger name="com.timmax.realestate" level="debug"/>

        //  Следовательно вывод log.debug() - будет в логах.
        log.debug("redirect to users");

        //  А вывод log.trace() - не будет.
        log.trace("(trace level) redirect to users");

        response.sendRedirect("users.jsp");
        //  Формат вывода по умолчанию будет:
        //  14:33:00.892 [http-nio-8080-exec-4] DEBUG com.timmax.realestate.web.UserServlet -- redirect to users
        //  Время
        //  Поток
        //  Уровень логирования
        //  Класс
        //  Сообщение
    }
}
