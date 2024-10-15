package com.example.quotivation.controller;

import com.example.quotivation.dto.author.response.AuthorInfo;
import com.example.quotivation.dto.category.response.CategoryInfo;
import com.example.quotivation.dto.quote.request.AddQuoteRequest;
import com.example.quotivation.dto.quote.response.QuoteDetail;
import com.example.quotivation.dto.quote.response.RelatedQuote;
import com.example.quotivation.service.AuthorService;
import com.example.quotivation.service.CategoryService;
import com.example.quotivation.service.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class QuoteController {

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final QuoteService quoteService;


    @GetMapping("/admin/add-quote")
    public String addQuotePage(Model model) {
        List<AuthorInfo> authors = authorService.getAuthorsForAddQuote();
        List<CategoryInfo> categories = categoryService.getCategoriesForAddQuote();

        model.addAttribute("authors", authors);
        model.addAttribute("categories", categories);

        return "add-quote-page";
    }

    @PostMapping("/admin/add-quote")
    public String addQuote(@RequestBody AddQuoteRequest quoteRequest) {
        quoteService.addQuote(quoteRequest);

        return "add-quote-page";
    }



    @GetMapping("/quote/{quoteId}")
    public String getQuoteDetailPage(Model model,
                                     @PathVariable Long quoteId) {
        QuoteDetail quoteDetail = quoteService.getQuoteDetail(quoteId);
        List<RelatedQuote> relatedQuotes = quoteService.getRelatedQuotes(quoteId);

        model.addAttribute("quote", quoteDetail);
        model.addAttribute("likeCount", quoteDetail.getLikeCount());
        model.addAttribute("dislikeCount", quoteDetail.getDislikeCount());
        model.addAttribute("isLike", quoteDetail.getIsLike());
        model.addAttribute("isDislike", quoteDetail.getIsDislike());
        model.addAttribute("relatedQuotes", relatedQuotes);

        return "quote-detail-page";
    }
}
