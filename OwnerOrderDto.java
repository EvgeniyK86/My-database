package org.myfirstdatabase.dto;

import lombok.Value;

@Value
public class OwnerOrderDto {
    private Long id;
    private Long ServicesId;
    private Long ServiceStationId;
    private Long CarId;
}
