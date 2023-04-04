package com.gft.api.services;

import com.gft.api.dto.tag.TagDTO;
import com.gft.api.dto.tag.TagHistoricDTO;
import com.gft.api.dto.tag.TagUpdateDTO;
import com.gft.api.entities.Tag;
import com.gft.api.repositories.TagRepository;
import com.gft.api.services.exceptions.DataBaseException;
import com.gft.api.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TagService {

    private final TagRepository tagRepository;

    @Transactional(readOnly = true)
    public Page<TagDTO> findAllPaged(Pageable pageable) {
        Page<Tag> list = tagRepository.findAll(pageable);
        return list.map(TagDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<TagHistoricDTO> topTrending(Pageable pageable) {
        Page<Tag> list = tagRepository.findAll(pageable);
        return list.map(TagHistoricDTO::new);
    }

    @Transactional(readOnly = true)
    public TagDTO findById(Long id) {
        Optional<Tag> obj = tagRepository.findById(id);
        Tag entity = obj.orElseThrow(() -> new ResourceNotFoundException("Tag not found"));
        return new TagDTO(entity);
    }

    @Transactional
    public TagDTO insert(TagDTO tagDTO) {
        Tag entity = new Tag();
        copyDtoToEntity(tagDTO, entity);
        entity = tagRepository.save(entity);
        return new TagDTO(entity);
    }

    @Transactional
    public TagDTO update(Long id, TagUpdateDTO tagUpdateDTO) {
        try {
            Tag entity = tagRepository.getReferenceById(id);
            copyDtoToEntity(tagUpdateDTO, entity);
            return new TagDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("ID " + id + " not found!");
        }
    }

    public void delete(Long id) {
        try {
            tagRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("ID " + id + " not found!");
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation!");
        }
    }

    public void copyDtoToEntity(TagDTO dto, Tag entity) {
        entity.setName(dto.getName());
    }
}