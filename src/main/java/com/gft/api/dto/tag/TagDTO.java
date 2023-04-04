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
public class TagDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    @NotBlank
    private String name;
    private Long views = 0L;

    public TagDTO(Tag tag) {
        id = tag.getId();
        name = tag.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagDTO tagDTO = (TagDTO) o;

        if (!id.equals(tagDTO.id)) return false;
        if (!name.equals(tagDTO.name)) return false;
        return views.equals(tagDTO.views);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + views.hashCode();
        return result;
    }
}