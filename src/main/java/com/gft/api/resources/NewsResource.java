package com.gft.api.resources;

import com.gft.api.dto.news.HistoricDTO;
import com.gft.api.dto.news.NewsList;
import com.gft.api.dto.tag.TagHistoricDTO;
import com.gft.api.entities.Tag;
import com.gft.api.entities.User;
import com.gft.api.repositories.UserRepository;
import com.gft.api.services.HistoricService;
import com.gft.api.services.NewsService;
import com.gft.api.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/news")
public class NewsResource {

    private final NewsService newsService;
    private final UserRepository userRepository;
    private final HistoricService historicService;
    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<NewsList>> findNews(Principal principal) {
        User user = userRepository.findByEmail(principal.getName());

        List<Tag> tags = user.getTags();
        List<String> tagName = new ArrayList<>();
        for (Tag tag : tags) {
            historicService.insertHistoric(user, tag);
            tagName.add(tag.getName());
        }
        return ResponseEntity.ok(newsService.news(tagName));
    }

    @GetMapping("/historic")
    public ResponseEntity<Page<HistoricDTO>> historicAll(@PageableDefault(sort = {"dateAccess"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(historicService.historicAll(pageable));
    }

    @GetMapping("/topTrending")
    public ResponseEntity<Page<TagHistoricDTO>> topTrending(@PageableDefault(sort = {"views"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(tagService.topTrending(pageable));
    }
}