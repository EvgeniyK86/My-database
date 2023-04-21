package org.myfirstdatabase.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerOrder {
    private Long id;
    private Long ServicesId;
    private Long ServiceStationId;
    private Long CarId;
}
