package org.myfirstdatabase.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.myfirstdatabase.entity.ServiceStation;
import org.myfirstdatabase.service.ServiceStationService;

import java.io.IOException;

@WebServlet("/content")
public class ContentServlet extends HttpServlet {
    private final ServiceStationService serviceStationService = ServiceStationService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var serviceStations = serviceStationService.findAll();
        req.setAttribute("serviceStations", serviceStations);
        req.getRequestDispatcher("/WEB-INF/jsp/content.jsp").forward(req, resp);
    }
}
