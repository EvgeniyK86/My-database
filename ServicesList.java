package org.myfirstdatabase.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicesList {
    private Long serviceStationId;
    private Long servicesId;
    private Long numberOfServices;
    private Long numberOfFree;
}
