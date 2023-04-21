package org.myfirstdatabase.dto;

import lombok.Value;

@Value
public class ServiceListDto {
    private Long serviceStationId;
    private Long servicesId;
    private Long numberOfServices;
    private Long numberOfFree;
}
