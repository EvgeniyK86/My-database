package org.myfirstdatabase.service;

import org.myfirstdatabase.dao.OwnerDao;
import org.myfirstdatabase.dao.ServicesDao;
import org.myfirstdatabase.dto.OwnerDto;
import org.myfirstdatabase.dto.ServicesDto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ServicesService {

    private final static ServicesService INSTANCE = new ServicesService();
    private final ServicesDao servicesDao = ServicesDao.getINSTANCE();

    public List<ServicesDto> findAllByServiceId(Long serviceId) {
        return servicesDao.findById(serviceId).stream().map(
                services -> new ServicesDto(
                        services.getId(),
                        services.getTypeOfService(),
                        services.getCost(),
                        services.getDuration()
                )
        ).collect(Collectors.toList());
    }

    public static ServicesService getInstance() {
        return INSTANCE;
    }

    private ServicesService() {
    }
}
