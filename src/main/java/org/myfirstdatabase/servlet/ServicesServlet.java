package org.myfirstdatabase.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.myfirstdatabase.service.ServicesService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/services")
public class ServicesServlet extends HttpServlet {

    private final ServicesService servicesService = ServicesService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        Long serviceId = Long.valueOf(req.getParameter("serviceId"));
        req.setAttribute("services", servicesService.findAllByServiceId(serviceId));
        req.getRequestDispatcher("/WEB-INF/jsp/services.jsp").forward(req, resp);

    }
}


