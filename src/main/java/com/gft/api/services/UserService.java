package com.gft.api.services;

import com.gft.api.dto.tag.TagDTO;
import com.gft.api.dto.user.RoleDTO;
import com.gft.api.dto.user.UserDTO;
import com.gft.api.dto.user.UserInsertDTO;
import com.gft.api.dto.user.UserUpdateDTO;
import com.gft.api.entities.Role;
import com.gft.api.entities.Tag;
import com.gft.api.entities.User;
import com.gft.api.repositories.RoleRepository;
import com.gft.api.repositories.TagRepository;
import com.gft.api.repositories.UserRepository;
import com.gft.api.services.exceptions.DataBaseException;
import com.gft.api.services.exceptions.ResourceNotFoundException;
import com.gft.api.services.exceptions.TagExistException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TagRepository tagRepository;
    private final EmailService emailService;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Pageable pageable) {
        Page<User> list = userRepository.findAll(pageable);
        return list.map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        Optional<User> obj = userRepository.findById(id);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        return new UserDTO(entity, entity.getTags());
    }

    @Transactional
    public UserDTO insert(UserInsertDTO userDTO) {
        User entity = new User();
        copyDtoToEntity(userDTO, entity);
        entity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        entity = userRepository.save(entity);
        emailService.sendEmail(entity.getEmail(), entity.getName(), "Cadastro realizado com sucesso.");
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO update(Long id, UserUpdateDTO userUpdateDTO) {
        try {
            User entity = userRepository.getReferenceById(id);
            copyDtoToEntity(userUpdateDTO, entity);
            return new UserDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("ID " + id + " not found!");
        }
    }

    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("ID " + id + " not found!");
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation!");
        }
    }

    @Transactional
    public void insertTag(String username, String name) {
        User user = userRepository.findByEmail(username);
        Optional<Tag> tagOptional = tagRepository.findByName(name);
        Tag tag;
        List<Tag> tags;
        tags = user.getTags();

        if (tagOptional.isPresent()) {
            if (tagRepository.existsByNameAndUsers_Email(name, user.getEmail())) {
                throw new TagExistException("Tag already registered to user");
            }
            tag = tagOptional.get();
            user.getTags().add(tag);
        } else {
            tag = new Tag();
            tag.setName(name);
            tag = tagRepository.save(tag);
            tags.add(tag);
            user.setTags(tags);
            userRepository.save(user);
        }
    }

    private void copyDtoToEntity(UserDTO dto, User entity) {
        entity.setEmail(dto.getEmail());
        entity.setName(dto.getName());

        entity.getRoles().clear();
        for (RoleDTO roleDto : dto.getRoles()) {
            Role role = roleRepository.getReferenceById(roleDto.getId());
            entity.getRoles().add(role);
        }

        for (TagDTO tagDTO : dto.getTags()) {
            Tag tag = tagRepository.getReferenceById(tagDTO.getId());
            entity.getTags().add(tag);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            logger.error("User not found: " + username);
            throw new UsernameNotFoundException("Email not found");
        }
        logger.info("User found: " + username);
        return user;
    }
}