package com.gft.api.dto.user;

import com.gft.api.dto.tag.TagDTO;
import com.gft.api.entities.Tag;
import com.gft.api.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String name;
    Set<RoleDTO> roles = new HashSet<>();
    private List<TagDTO> tags = new ArrayList<>();

    public UserDTO(User entity) {
        id = entity.getId();
        email = entity.getEmail();
        name = entity.getName();
        entity.getRoles().forEach(role -> this.roles.add(new RoleDTO(role)));
    }

    public UserDTO(User entity, List<Tag> tags) {
        this(entity);
        tags.forEach(tag -> this.tags.add(new TagDTO(tag)));
    }
}