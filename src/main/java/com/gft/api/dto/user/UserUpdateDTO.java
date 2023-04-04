package com.gft.api.dto.user;

import com.gft.api.services.validation.user.UserUpdateValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
@AllArgsConstructor
@UserUpdateValid
public class UserUpdateDTO extends UserDTO {

    @Serial
    private static final long serialVersionUID = 1L;

}