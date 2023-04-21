package org.myfirstdatabase.dto;

import lombok.Value;

import java.util.Date;

@Value
public class ServicesDto {
    private Long id;
    private String typeOfService;
    private Integer cost;
    private Date duration;

}
