package org.myfirstdatabase.dto;

import lombok.Value;

import java.time.LocalDate;

@Value
public class CarDto {
    private Long id;
    private Long ownerId;
    private String model;
    private Double engineCapacity;
    private String engineType;
    private LocalDate year;
}
