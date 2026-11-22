package com.cesul.barber.utils;

import com.cesul.barber.dtos.UserCreationDTO;
import com.cesul.barber.entity.Users;

public class Mapper {

    public static UserCreationDTO usersToUserCreationDTO(Users users){
        return new UserCreationDTO(
                users.getId(),
                users.getName(),
                users.getEmail(),
                users.getAvatarUrl()
        );
    }
}
