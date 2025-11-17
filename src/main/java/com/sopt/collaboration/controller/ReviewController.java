package com.sopt.collaboration.controller;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sopt.collaboration.dto.ReviewResponseDto;
import com.sopt.collaboration.global.response.CommonApiResponse;
import com.sopt.collaboration.global.response.success.SuccessCode;
import com.sopt.collaboration.service.ReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "Review", description = "리뷰 관련 API")
public class ReviewController {

	private final ReviewService reviewService;

	@Operation(summary = "리뷰 좋아요 토글", description = "리뷰의 좋아요를 토글합니다. 좋아요가 되어있으면 취소, 안되어있으면 좋아요 처리됩니다.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "좋아요 토글 성공"),
			@ApiResponse(responseCode = "404", description = "리뷰를 찾을 수 없음")
	})
	@PatchMapping("/{reviewId}/like")
	public CommonApiResponse<ReviewResponseDto> toggleLike(
			@Parameter(description = "리뷰 ID", required = true) @PathVariable Long reviewId) {
		ReviewResponseDto review = reviewService.toggleLike(reviewId);
		return CommonApiResponse.success(SuccessCode.REVIEW_LIKE_TOGGLED, review);
	}
}
