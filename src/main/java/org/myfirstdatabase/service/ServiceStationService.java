package org.myfirstdatabase.service;

import org.myfirstdatabase.dao.ServiceStationDao;
import org.myfirstdatabase.dao.ServicesDao;
import org.myfirstdatabase.dto.ServiceStationDto;
import org.myfirstdatabase.dto.ServicesDto;

import java.util.List;
import java.util.stream.Collectors;

public class ServiceStationService {

    private final static ServiceStationService INSTANCE = new ServiceStationService();
    private final ServiceStationDao serviceStationDao = ServiceStationDao.getINSTANCE();

    public List<ServiceStationDto> findAll() {
        return serviceStationDao.findAll().stream().map(
                serviceStation -> new ServiceStationDto(
                        serviceStation.getId(),
                        serviceStation.getServiceStationName()
                )
        ).collect(Collectors.toList());
    }

    public static ServiceStationService getInstance() {
        return INSTANCE;
    }

    private ServiceStationService() {
    }

}
