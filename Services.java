package org.myfirstdatabase.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Services {
    private Long id;
    private String typeOfService;
    private Integer cost;
    private Date duration;
}
