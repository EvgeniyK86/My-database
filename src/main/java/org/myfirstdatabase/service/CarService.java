package org.myfirstdatabase.service;

import org.myfirstdatabase.dao.CarDao;
import org.myfirstdatabase.dto.CarDto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CarService {

    private final static CarService INSTANCE = new CarService();
    private final CarDao carDao = CarDao.getINSTANCE();

    public List<CarDto> findAll() {
        return carDao.findAll().stream().map(
                car -> new CarDto(
                        car.getId(),
                        car.getOwnerId(),
                        car.getModel(),
                        car.getEngineCapacity(),
                        car.getEngineType(),
                        car.getYear()
                )
        ).collect(Collectors.toList());
    }

    public static CarService getInstance() {
        return INSTANCE;
    }

    private CarService() {
    }
}
