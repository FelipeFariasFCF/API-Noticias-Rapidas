package com.gft.api.resources;

import com.gft.api.dto.tag.TagDTO;
import com.gft.api.dto.tag.TagUpdateDTO;
import com.gft.api.services.TagService;
import com.gft.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/tags")
public class TagResource {

    private final TagService tagService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<TagDTO>> findAll(Pageable pageable) {
        Page<TagDTO> list = tagService.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("{id}")
    public ResponseEntity<TagDTO> findById(@PathVariable Long id) {
        TagDTO dto = tagService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<TagDTO> insert(@Valid @RequestBody TagDTO tagDTO) {
        TagDTO newDTO = tagService.insert(tagDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(newDTO);
    }

    @PostMapping("/user")
    public ResponseEntity<Void> insertTagUser(Principal principal, @Valid @RequestBody TagDTO tagDTO) {
        userService.insertTag(principal.getName(), tagDTO.getName());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<TagDTO> update(@PathVariable Long id, @Valid @RequestBody TagUpdateDTO tagDTO) {
        TagDTO newDTO = tagService.update(id, tagDTO);
        return ResponseEntity.ok().body(newDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}