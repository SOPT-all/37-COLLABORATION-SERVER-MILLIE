package com.sopt.collaboration.dto;

import com.sopt.collaboration.entity.Book;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
@Schema(description = "책 요약 정보 DTO")
public class BookSummaryDto {

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

	@Schema(description = "오디어북 여부", example = "FALSE")
	private Boolean isAudiobook;

	@Schema(description = "성우 이름", example = "임은지")
	private String voiceActor;

	public static BookSummaryDto from(Book book, String imageUrl) {
		return BookSummaryDto.builder()
			.bookId(book.getBookId())
			.bookCoverImageUrl(imageUrl)
			.bookTitle(book.getTitle())
			.bookAuthor(book.getAuthorName())
			.completionRate(book.getFullReadRate())
			.completionTime(book.getCompletionTime())
			.isAudiobook(book.getIsAudiobook())
			.voiceActor(book.getVoiceActor())
			.build();
	}
}
