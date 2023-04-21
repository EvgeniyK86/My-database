package org.myfirstdatabase.mapper;

import lombok.NoArgsConstructor;
import org.myfirstdatabase.dto.CreateUserDto;
import org.myfirstdatabase.entity.Gender;
import org.myfirstdatabase.entity.Role;
import org.myfirstdatabase.entity.User;
import org.myfirstdatabase.utils.LocalDateFormatter;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateUserMapper implements Mapper<CreateUserDto, User> {

    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    @Override
    public User mapFrom(CreateUserDto object) {
        return User.builder()
                .name(object.getName())
                .birthday(LocalDateFormatter.format(object.getBirthday()))
                .email(object.getEmail())
                .password(object.getPassword())
                .gender(Gender.valueOf(object.getGender()))
                .role(Role.valueOf(object.getRole()))
                .build();
    }

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }
}
