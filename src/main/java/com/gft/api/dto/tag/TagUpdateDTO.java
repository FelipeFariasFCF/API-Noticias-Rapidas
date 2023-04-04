package com.gft.api.dto.tag;

import com.gft.api.services.validation.tag.TagUpdateValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
@AllArgsConstructor
@TagUpdateValid
public class TagUpdateDTO extends TagDTO {

    @Serial
    private static final long serialVersionUID = 1L;

}