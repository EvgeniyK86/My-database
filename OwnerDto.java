package org.myfirstdatabase.dto;

import lombok.Value;
import org.myfirstdatabase.entity.OwnerStatus;

@Value
public class OwnerDto {
    private Long id;
    private String passportNo;
    private String ownerName;
    private String email;
    private String phoneNumber;
    private OwnerStatus status;
}
