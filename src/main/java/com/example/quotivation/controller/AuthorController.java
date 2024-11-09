package com.example.quotivation.controller;

import com.example.quotivation.dto.author.response.AuthorDescriptionResponse;
import com.example.quotivation.dto.author.response.AuthorDetailsResponse;
import com.example.quotivation.dto.author.response.AuthorListInfo;
import com.example.quotivation.dto.author.response.admin.AuthorUpdateListResponse;
import com.example.quotivation.dto.quote.response.QuoteListInfo;
import com.example.quotivation.service.AuthorService;
import com.example.quotivation.service.QuoteService;
import com.example.quotivation.service.UserAuthorSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final QuoteService quoteService;
    private final AuthorService authorService;
    private final UserAuthorSubscriptionService userAuthorSubscriptionService;

    @GetMapping("/admin/add-author")
    public String getAddAuthorPage() {
        return "admin/add-author-page";
    }

    @PostMapping("/admin/add-author")
    public String addAuthor(@RequestPart("image") MultipartFile image,
                            @RequestParam("saveName") String saveName,
                            @RequestParam("name") String name,
                            @RequestParam("description") String description) throws IOException {
        authorService.addAuthor(name, image, saveName, description);

        return "admin/add-author-page";
    }

    @GetMapping("/admin/update-author")
    public String getUpdateAuthorPage(Model model) {
        AuthorUpdateListResponse response = authorService.getEnglishAuthors();

        model.addAttribute("count", response.getCount());
        model.addAttribute("authors", response.getAuthors());

        return "admin/update-author-page";
    }

    @PostMapping("/admin/update-author")
    public String updateAuthor(@RequestParam("id") Long id,
                               @RequestParam("newName") String newName) {
        authorService.updateEnglishAuthor(id, newName);

        return "admin/update-author-page";
    }

    @GetMapping("/authors")
    public String getAuthorPage(Model model,
                                @RequestParam(value = "order", defaultValue = "alpha") String order,
                                @PageableDefault(size = 10) Pageable pageable) {
        AuthorListInfo response = authorService.getAllAuthors(pageable, order);

        if(response.getTotalPages() < pageable.getPageNumber())
            return String.format("redirect:/authors?page=%d&order=%s", response.getTotalPages(), order);

        model.addAttribute("totalPages", response.getTotalPages());
        model.addAttribute("authors", response.getAuthors());
        model.addAttribute("currentPage", pageable.getPageNumber() + 1);
        model.addAttribute("sort", order);

        return "author/authors-page";
    }

    @GetMapping("/author/{authorId}")
    public String getAuthorQuotesPage(Model model,
                                      @PageableDefault(size = 10) Pageable pageable,
                                      @PathVariable Long authorId) {
        QuoteListInfo response = quoteService.getQuotesByAuthor(authorId, pageable);
        AuthorDescriptionResponse author = authorService.getAuthorNameAndDescription(authorId);

        if(response.getTotalPages() < pageable.getPageNumber())
            return String.format("redirect:/author/%d?page=%d", authorId, response.getTotalPages());

        model.addAttribute("quotes", response.getQuotes());
        model.addAttribute("currentPage", pageable.getPageNumber() + 1);
        model.addAttribute("totalPages", response.getTotalPages());
        model.addAttribute("count", response.getCount());
        model.addAttribute("authorId", authorId);
        model.addAttribute("authorName", author.getName());
        model.addAttribute("authorDescription", author.getDescription());

        return "author/author-quotes-page";
    }

    @GetMapping("/author/{authorId}/details")
    public String getAuthorDetailsPage(@PathVariable Long authorId,
                                       Model model) {
        AuthorDetailsResponse authorDetails = authorService.getAuthorDetails(authorId);
        Boolean isSubscribe = userAuthorSubscriptionService.isSubscribe(authorId);

        model.addAttribute("details", authorDetails);
        model.addAttribute("isSubscribe", isSubscribe);

        return "author/author-detail-page";
    }
}
