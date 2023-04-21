package org.myfirstdatabase.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceStationAddress {
    private Long serviceStationId;
    private String city;
    private String street;
    private String house;
    private String email;
    private String phoneNumber;
}
