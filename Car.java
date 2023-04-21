package org.myfirstdatabase.entity;

import lombok.*;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {
    private Long id;
    private Long ownerId;
    private String model;
    private double engineCapacity;
    private String engineType;
    private LocalDate year;
}

