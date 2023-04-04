package com.gft.api.dto.tag;

import com.gft.api.entities.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagHistoricDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    @NotBlank
    private String name;
    private Long views;

    public TagHistoricDTO(Tag tag) {
        id = tag.getId();
        name = tag.getName();
        views = tag.getViews();
    }
}