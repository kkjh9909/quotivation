package com.example.quotivation.service;

import com.example.quotivation.dto.author.request.AddAuthorRequest;
import com.example.quotivation.dto.author.response.*;
import com.example.quotivation.entity.Author;
import com.example.quotivation.entity.Quote;
import com.example.quotivation.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final ImageUploadService imageUploadService;

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

    public AuthorDescriptionResponse getAuthorNameAndDescription(Long authorId) {
        Optional<Author> author = authorRepository.findById(authorId);

        return AuthorDescriptionResponse.make(author.get());
    }

    public List<AuthorInfo> getAuthorsForAddQuote() {
        List<Author> authors = authorRepository.findAll();

        return authors.stream().map(AuthorInfo::make).collect(Collectors.toList());
    }

    @Transactional
    public void addAuthor(String name, MultipartFile image, String saveName, String description) throws IOException {
        imageUploadService.uploadImage(image, saveName);
        String imageUrl = imageUploadService.getImageUrl();

        Author author = Author.make(name, description, imageUrl);

        authorRepository.save(author);
    }

    public SearchAuthorListResponse searchAuthorsPreview(String query) {
        List<Author> authors = authorRepository.findByNameContainingOrderByCreatedAtDesc(query, PageRequest.of(0, 5));
        Long totalCount = authorRepository.countByNameContaining(query);

        return new SearchAuthorListResponse(authors.stream().map(SearchAuthorResponse::make).collect(Collectors.toList()),
                authors.size(), totalCount);
    }

    public SearchAuthorListResponse searchAuthorsByPage(String query, Pageable pageable) {
        List<Author> authors = authorRepository.findByNameContainingOrderByCreatedAtDesc(query, pageable);
        Long totalCount = authorRepository.countByNameContaining(query);

        return new SearchAuthorListResponse(authors.stream().map(SearchAuthorResponse::make).collect(Collectors.toList()),
                authors.size(), totalCount);
    }
}
