package org.myfirstdatabase.service;

import org.myfirstdatabase.dao.CarDao;
import org.myfirstdatabase.dao.OwnerDao;
import org.myfirstdatabase.dto.CarDto;
import org.myfirstdatabase.dto.OwnerDto;

import java.util.List;
import java.util.stream.Collectors;

public class OwnerService {
    private final static OwnerService INSTANCE = new OwnerService();
    private final OwnerDao ownerDao = OwnerDao.getINSTANCE();

    public List<OwnerDto> findAll() {
        return ownerDao.findAll().stream().map(
                owner -> new OwnerDto(
                        owner.getId(),
                        owner.getPassportNo(),
                        owner.getOwnerName(),
                        owner.getEmail(),
                        owner.getPhoneNumber(),
                        owner.getStatus()
                )
        ).collect(Collectors.toList());
    }

    public static OwnerService getInstance() {
        return INSTANCE;
    }

    private OwnerService() {
    }

}
