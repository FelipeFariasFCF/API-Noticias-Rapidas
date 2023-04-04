package com.gft.api.services;

import com.gft.api.dto.news.HistoricDTO;
import com.gft.api.entities.Historic;
import com.gft.api.entities.Tag;
import com.gft.api.entities.User;
import com.gft.api.repositories.HistoricRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class HistoricService {

    private final HistoricRepository historicRepository;

    @Transactional
    public void insertHistoric(User user, Tag tag) {
        Historic historic = new Historic(user, tag);
        historicRepository.save(historic);
    }

    @Transactional(readOnly = true)
    public Page<HistoricDTO> historicAll(Pageable pageable) {
        Page<Historic> list = historicRepository.findAll(pageable);
        return list.map(HistoricDTO::new);
    }
}