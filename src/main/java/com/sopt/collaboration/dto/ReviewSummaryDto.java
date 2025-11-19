package com.sopt.collaboration.dto;

import com.sopt.collaboration.entity.Review;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewSummaryDto {
    private Long reviewId;
    private String reviewAuthor;
    private String reviewCreatedAt;
    private String reviewContent;
    private boolean isLiked;
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
