package com.sopt.collaboration.dto;

import com.sopt.collaboration.entity.Banner;
import com.sopt.collaboration.entity.Book;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Builder
@Getter
@AllArgsConstructor
@Schema(description = "책 검색 결과 응답 DTO")
public class BookSearchResponseDto {

    @Schema(description = "검색 키워드", example = "홍학")
    private String keyword;

    @Schema(description = "검색된 책 리스트")
    private List<BookSummaryDto> books;

    @Schema(description = "배너 정보")
    private BannerDto banner;

    @Builder
    @Getter
    @AllArgsConstructor
    @Schema(description = "책 요약 정보 DTO")
    public static class BookSummaryDto {

        @Schema(description = "책 ID", example = "1")
        private Long bookId;

        @Schema(description = "책 표지 이미지 URL", example = "https://image.millie.co.kr/covers/1234.jpg")
        private String bookCoverImageUrl;

        @Schema(description = "책 제목", example = "홍학의 자리")
        private String bookTitle;

        @Schema(description = "작가명", example = "정해연")
        private String bookAuthor;

        @Schema(description = "완독률", example = "78")
        private Integer completionRate;

        @Schema(description = "완독 예상 시간", example = "240")
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
    @Schema(description = "배너 정보 DTO")
    public static class BannerDto {

        @Schema(description = "배너 ID", example = "10")
        private Long bannerId;

        @Schema(description = "배너 제목", example = "《홍학의 자리》 읽을 준비!")
        private String bannerTitle;

        @Schema(description = "배너 내용", example = "이 책부터 읽어야 재미가 2배")
        private String bannerContent;

        @Schema(description = "배너 이미지 URL", example = "https://image.millie.co.kr/banners/5678.jpg")
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
