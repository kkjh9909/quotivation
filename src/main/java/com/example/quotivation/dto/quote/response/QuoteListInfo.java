package com.example.quotivation.dto.quote.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class QuoteListInfo {

    private List<QuoteList> quotes;
    private Long count;
    private int totalPages;
}
