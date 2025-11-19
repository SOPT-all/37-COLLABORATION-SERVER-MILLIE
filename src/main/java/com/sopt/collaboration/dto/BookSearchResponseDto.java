package com.sopt.collaboration.dto;

import java.util.List;
import java.util.Map;

import com.sopt.collaboration.entity.Banner;
import com.sopt.collaboration.entity.Book;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
@Schema(description = "책 검색 결과 응답 DTO")
public class BookSearchResponseDto {

	@Schema(description = "검색 키워드", example = "홍학")
	private String keyword;

	@Schema(description = "검색된 책 개수")
	private int bookCount;

	@Schema(description = "검색된 책 리스트")
	private List<BookSummaryDto> books;

	@Schema(description = "배너 정보")
	private BannerDto banner;

	public static BookSearchResponseDto from(
		String keyword,
		List<Book> books,
		Banner banner,
		Map<Long, String> bookImageMap,
		String bannerImageUrl
	) {
		List<BookSummaryDto> bookSummaries = books.stream()
			.map(book -> BookSummaryDto.from(book, bookImageMap.get(book.getBookId())))
			.toList();

		BannerDto bannerDto = BannerDto.from(banner, bannerImageUrl);

		return BookSearchResponseDto.builder()
			.keyword(keyword)
			.bookCount(books.size())
			.banner(bannerDto)
			.books(bookSummaries)
			.build();
	}
}
