package com.gft.api.services.validation.tag;

import com.gft.api.dto.tag.TagUpdateDTO;
import com.gft.api.entities.Tag;
import com.gft.api.repositories.TagRepository;
import com.gft.api.resources.exceptions.FieldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class TagUpdateValidator implements ConstraintValidator<TagUpdateValid, TagUpdateDTO> {

    private final HttpServletRequest request;
    private final TagRepository tagRepository;

    @Override
    public void initialize(TagUpdateValid ann) {
    }

    @Override
    public boolean isValid(TagUpdateDTO dto, ConstraintValidatorContext context) {

        @SuppressWarnings("unchecked")
        var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long tagId = Long.parseLong(uriVars.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        Optional<Tag> tag = tagRepository.findByName(dto.getName());
        if (tag.isPresent() && tagId != tag.get().getId()) {
            list.add(new FieldMessage("name", "Nome já cadastrado."));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}