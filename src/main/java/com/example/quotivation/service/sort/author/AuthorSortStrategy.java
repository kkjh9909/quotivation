package com.example.quotivation.service.sort.author;

import com.example.quotivation.dto.author.response.AuthorListInfo;
import org.springframework.data.domain.Pageable;

public interface AuthorSortStrategy {

    AuthorListInfo sort(Pageable pageable);
}
