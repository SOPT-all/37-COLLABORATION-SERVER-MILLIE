package com.sopt.collaboration.dto;

import com.sopt.collaboration.entity.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "리뷰 요약 정보 DTO (도서 상세 조회용)")
public class ReviewSummaryDto {

    @Schema(description = "리뷰 ID", example = "1")
    private Long reviewId;

    @Schema(description = "리뷰 작성자", example = "어진")
    private String reviewAuthor;

    @Schema(description = "작성일", example = "2024.05.06")
    private String reviewCreatedAt;

    @Schema(description = "리뷰 내용", example = "저는 책에 오타가 난 줄 알았어요")
    private String reviewContent;

    @Schema(description = "좋아요 여부", example = "false")
    private boolean isLiked;

    @Schema(description = "좋아요 수", example = "42")
    private int likeCount;

    public static ReviewSummaryDto from(Review review) {
        return ReviewSummaryDto.builder()
                .reviewId(review.getId())
                .reviewAuthor(review.getReviewerName())
                .reviewCreatedAt(review.getCreatedDate())
                .reviewContent(review.getContent())
                .isLiked(review.isLiked())
                .likeCount(review.getLikeCount())
                .build();
    }
}
