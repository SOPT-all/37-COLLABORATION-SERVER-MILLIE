package com.sopt.collaboration.dto;

import com.sopt.collaboration.entity.Book;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(description = "도서 상세 정보 응답 DTO")
public class BookDetailResponseDto {

    private Long bookId;
    private String bookCoverImageUrl;
    private String bookTitle;
    private String bookAuthor;
    private String bookType;
    private String publishedDate;
    private Float bookRate;
    private Integer totalReviewCount;
    private Integer completionRate;
    private String bookDescription;
    private List<ReviewSummaryDto> reviews;

    public static BookDetailResponseDto from(
            Book book,
            String bookImageUrl,
            List<ReviewSummaryDto> reviewDtos
    ) {
        return BookDetailResponseDto.builder()
                .bookId(book.getBookId())
                .bookCoverImageUrl(bookImageUrl)
                .bookTitle(book.getTitle())
                .bookAuthor(book.getAuthorName())
                .bookType(book.getNovelType())
                .publishedDate(book.getPublishedDate())
                .bookRate(book.getRating())
                .totalReviewCount(book.getReviews().size())
                .completionRate(book.getFullReadRate())
                .bookDescription(book.getIntroduce())
                .reviews(reviewDtos)
                .build();
    }
}
