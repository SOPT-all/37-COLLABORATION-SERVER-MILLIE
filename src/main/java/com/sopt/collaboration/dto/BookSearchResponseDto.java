package com.sopt.collaboration.dto;

import com.sopt.collaboration.entity.Banner;
import com.sopt.collaboration.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Builder
@Getter
@AllArgsConstructor
public class BookSearchResponseDto {
    private String keyword;
    private List<BookSummaryDto> books;
    private BannerDto banner;

    @Builder
    @Getter
    @AllArgsConstructor
    public static class BookSummaryDto {
        private Long bookId;
        private String bookCoverImageUrl;
        private String bookTitle;
        private String bookAuthor;
        private Integer completionRate;
        private Integer completionTime;

        public static BookSummaryDto from(Book book, String imageUrl) {
            return BookSummaryDto.builder()
                    .bookId(book.getBookId())
                    .bookCoverImageUrl(imageUrl)
                    .bookTitle(book.getTitle())
                    .bookAuthor(book.getAuthorName())
                    .completionRate(book.getFullReadRate())
                    .completionTime(book.getCompletionTime())
                    .build();
        }
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class BannerDto {
        private Long bannerId;
        private String bannerTitle;
        private String bannerContent;
        private String bannerImageUrl;

        public static BannerDto from(Banner banner, String imageUrl) {
            return BannerDto.builder()
                    .bannerId(banner.getBannerId())
                    .bannerTitle(banner.getTitle())
                    .bannerContent(banner.getContent())
                    .bannerImageUrl(imageUrl)
                    .build();
        }
    }

    public static BookSearchResponseDto from(
            String keyword,
            List<Book> books,
            Banner banner,
            Map<Long, String> bookImageMap, // 책 ID별 이미지 URL
            String bannerImageUrl
    ) {
        return BookSearchResponseDto.builder()
                .keyword(keyword)
                .banner(BannerDto.from(banner, bannerImageUrl))
                .books(books.stream()
                        .map(book -> BookSummaryDto.from(
                                book,
                                bookImageMap.get(book.getBookId())
                        ))
                        .toList())
                .build();
    }
}
