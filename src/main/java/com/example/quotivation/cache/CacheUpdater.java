package com.example.quotivation.cache;

import com.example.quotivation.dto.quote.response.LatestQuote;
import com.example.quotivation.service.QuoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class CacheUpdater {

    private final CacheManager cacheManager;
    private final QuoteService quoteService;

    @Scheduled(fixedRate = 3600000)
    public void updateCacheEveryDay() {
        Objects.requireNonNull(cacheManager.getCache("quotes")).clear();

        List<LatestQuote> quotes = quoteService.getLatestQuotes();
        Objects.requireNonNull(cacheManager.getCache("quotes")).put("latest", quotes);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void removeCacheEveryHour() {
        Cache quotesCache = cacheManager.getCache("quotes");
        if (quotesCache != null) {
            quotesCache.evict("quotes");
        }
    }
}
