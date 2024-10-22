package com.example.quotivation.service.sort.author;

import com.example.quotivation.dto.author.response.AuthorInfo;
import com.example.quotivation.dto.author.response.AuthorListInfo;
import com.example.quotivation.entity.Author;
import com.example.quotivation.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service("name")
@RequiredArgsConstructor
public class AuthorSortNameStrategy implements AuthorSortStrategy {

    private final AuthorRepository authorRepository;

    @Override
    public AuthorListInfo sort(Pageable pageable) {
        Page<Author> authors = authorRepository.findAllByOrderByQuoteCountDesc(pageable);

        return new AuthorListInfo(authors.getContent().stream().map(AuthorInfo::make).collect(Collectors.toList()),
                authors.getTotalPages());
    }
}
