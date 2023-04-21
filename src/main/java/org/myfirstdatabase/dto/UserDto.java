package org.myfirstdatabase.dto;

import lombok.Builder;
import lombok.Value;
import org.myfirstdatabase.entity.Gender;
import org.myfirstdatabase.entity.Role;

import java.time.LocalDate;

@Value
@Builder
public class UserDto {
    Integer id;
    String name;
    LocalDate birthday;
    String email;
    Role role;
    Gender gender;
}