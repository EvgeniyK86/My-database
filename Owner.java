package org.myfirstdatabase.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Owner {
    private Long id;
    private String passportNo;
    private String ownerName;
    private String email;
    private String phoneNumber;
    private OwnerStatus status;
}
