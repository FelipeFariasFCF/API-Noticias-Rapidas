package com.gft.api.dto.user;

import com.gft.api.services.validation.user.UserInsertValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serial;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@UserInsertValid
public class UserInsertDTO extends UserDTO {

    @Serial
    private static final long serialVersionUID = 1L;

    @Size(min = 8, max = 20, message = "Min 8, Max 20")
    @NotBlank
    private String password;

}