package org.myfirstdatabase.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicesHistory {
    private Long ownerId;
    private Long servicesId;
    private Integer numberOfRequest;
}
