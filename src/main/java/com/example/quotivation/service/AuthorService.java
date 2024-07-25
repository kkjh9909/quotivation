package com.example.quotivation.service;

import com.example.quotivation.dto.author.response.AuthorInfo;
import com.example.quotivation.dto.author.response.AuthorListInfo;
import com.example.quotivation.entity.Author;
import com.example.quotivation.entity.Quote;
import com.example.quotivation.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public List<AuthorInfo> getAuthors() {
        List<Author> authors = authorRepository.findAll(PageRequest.of(0, 10)).getContent();

        return authors.stream().map(AuthorInfo::make).collect(Collectors.toList());
    }


    public AuthorListInfo getAllAuthors(Pageable pageable, String order) {
        Page<Author> authors;
        if(order.equals("popular"))
            authors = authorRepository.findAllByOrderByQuoteCountDesc(pageable);
        else
            authors = authorRepository.findAllByOrderByName(pageable);

        return new AuthorListInfo(authors.getContent().stream().map(AuthorInfo::make).collect(Collectors.toList()),
                authors.getTotalPages());
    }

    public String getAuthorName(Long authorId) {
        Optional<Author> author = authorRepository.findById(authorId);

        return author.get().getName();
    }
}
