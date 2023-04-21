package org.myfirstdatabase.service;

import org.myfirstdatabase.dao.ServiceStationAddressDao;
import org.myfirstdatabase.dao.ServiceStationDao;
import org.myfirstdatabase.dto.ServiceStationAddressDto;
import org.myfirstdatabase.dto.ServiceStationDto;

import java.util.List;
import java.util.stream.Collectors;

public class ServiceStationAddressService {

    private final static ServiceStationAddressService INSTANCE = new ServiceStationAddressService();
    private final ServiceStationAddressDao serviceStationAddressDao = ServiceStationAddressDao.getINSTANCE();

    public List<ServiceStationAddressDto> findAll() {
        return serviceStationAddressDao.findAll().stream().map(
                serviceStationAddress -> new ServiceStationAddressDto(
                        serviceStationAddress.getServiceStationId(),
                        serviceStationAddress.getCity(),
                        serviceStationAddress.getStreet(),
                        serviceStationAddress.getHouse(),
                        serviceStationAddress.getEmail(),
                        serviceStationAddress.getPhoneNumber()
                )
        ).collect(Collectors.toList());
    }

    public static ServiceStationAddressService getInstance() {
        return INSTANCE;
    }

    private ServiceStationAddressService() {
    }
}
