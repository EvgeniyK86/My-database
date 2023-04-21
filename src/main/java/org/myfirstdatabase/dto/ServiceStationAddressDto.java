package org.myfirstdatabase.dto;

import lombok.Value;

@Value
public class ServiceStationAddressDto {
    private Long serviceStationId;
    private String city;
    private String street;
    private String house;
    private String email;
    private String phoneNumber;
}
