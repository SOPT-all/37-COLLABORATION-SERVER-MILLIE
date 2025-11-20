package com.sopt.collaboration.dto;

import java.util.List;

import com.sopt.collaboration.entity.Book;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "도서 상세 정보 응답 DTO")
public class BookDetailResponseDto {

	@Schema(description = "책 ID", example = "1")
	private Long bookId;

	@Schema(description = "책 표지 이미지 URL",
		example = "https://millie-s3-bucket/.../BOOK_IMAGE_1")
	private String bookCoverImageUrl;

	@Schema(description = "책 제목", example = "홍학의 자리")
	private String bookTitle;

	@Schema(description = "작가명", example = "정해연")
	private String bookAuthor;

	@Schema(description = "책 유형", example = "소설")
	private String bookType;

	@Schema(description = "출판일", example = "2021.07.21")
	private String publishedDate;

	@Schema(description = "평점", example = "3.9")
	private Float bookRate;

	@Schema(description = "총 리뷰 개수", example = "2")
	private Integer totalReviewCount;

	@Schema(description = "완독률", example = "80")
	private Integer completionRate;

	@Schema(description = "책 소개",
		example = "“이 행복이 영원할 거라고 생각한 적은 없었다...”")
	private String bookDescription;

	@Schema(description = "리뷰 리스트")
	private List<ReviewResponseDto> reviews;

	public static BookDetailResponseDto from(
		Book book,
		String bookImageUrl,
		List<ReviewResponseDto> reviews
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
			.reviews(reviews)
			.build();
	}
}
