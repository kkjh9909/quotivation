package com.example.quotivation.service;

import com.example.quotivation.dto.author.response.AuthorInfo;
import com.example.quotivation.entity.Author;
import com.example.quotivation.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public List<AuthorInfo> getAuthors() {
        List<Author> authors = authorRepository.findAll(PageRequest.of(0, 10)).getContent();

        return authors.stream().map(AuthorInfo::make).collect(Collectors.toList());
    }


}
