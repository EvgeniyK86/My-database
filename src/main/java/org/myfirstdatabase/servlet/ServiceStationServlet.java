package org.myfirstdatabase.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.myfirstdatabase.service.ServiceStationService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/serviceStations")
public class ServiceStationServlet extends HttpServlet {

    private final ServiceStationService serviceStationService = ServiceStationService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try (var writer = resp.getWriter()) {
            writer.write("<h1>Список CTO: </h1>");
            writer.write("<ul>");
            serviceStationService.findAll().stream().forEach(serviceStationDto -> {
                        writer.write("""
                                    <li>
                                    <a href='/services?serviceStationId=%d'>%s</a>
                                    </li>
                                """.formatted(serviceStationDto));
                    }
            );
            writer.write("</ul>");
        }
    }
}

