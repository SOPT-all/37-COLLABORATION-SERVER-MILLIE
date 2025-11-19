package com.sopt.collaboration.dto;

import com.sopt.collaboration.entity.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "리뷰 정보 응답")
public class ReviewResponseDto {

	@Schema(description = "리뷰 ID", example = "1")
	private Long reviewId;

	@Schema(description = "책 ID", example = "1")
	private Long bookId;

	@Schema(description = "리뷰어 이름", example = "홍길동")
	private String reviewerName;

	@Schema(description = "작성일", example = "2024.01.15")
	private String createdDate;

	@Schema(description = "리뷰 내용", example = "정말 좋은 책이었습니다.")
	private String reviewContent;

	@Schema(description = "좋아요 여부", example = "true")
	private boolean isLiked;

	@Schema(description = "좋아요 수", example = "10")
	private int likeCount;

	public static ReviewResponseDto from(Review review) {
		return ReviewResponseDto.builder()
				.reviewId(review.getId())
				.bookId(review.getBook().getBookId())
				.reviewerName(review.getReviewerName())
				.createdDate(review.getCreatedDate())
				.reviewContent(review.getContent())
				.likeCount(review.getLikeCount())
				.isLiked(review.isLiked())
				.build();
	}
}
