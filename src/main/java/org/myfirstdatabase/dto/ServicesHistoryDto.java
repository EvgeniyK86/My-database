package org.myfirstdatabase.dto;

import lombok.Value;

@Value
public class ServicesHistoryDto {
    private Long ownerId;
    private Long servicesId;
    private Integer numberOfRequest;

}
