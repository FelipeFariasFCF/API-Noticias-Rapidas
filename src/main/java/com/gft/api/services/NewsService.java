package com.gft.api.services;

import com.gft.api.dto.news.NewsList;
import com.gft.api.entities.Tag;
import com.gft.api.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NewsService {

    private final WebClient webClient;
    private final TagRepository tagRepository;

    @Transactional
    public List<NewsList> news(List<String> tags) {

        LocalDate dateNow = LocalDate.now();
        String date = dateNow.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        List<NewsList> newsLists = new ArrayList<>();

        for (String tag : tags) {

            // add views
            Optional<Tag> entity = tagRepository.findByName(tag);
            entity.get().setViews(entity.get().getViews() + 1L);

            Mono<NewsList> mono = this.webClient
                    .method(HttpMethod.GET)
                    .uri("/?q=" + tag + "&date=" + date)
                    .retrieve()
                    .bodyToMono(NewsList.class);
            newsLists.add(mono.block());
        }
        return newsLists;
    }
}