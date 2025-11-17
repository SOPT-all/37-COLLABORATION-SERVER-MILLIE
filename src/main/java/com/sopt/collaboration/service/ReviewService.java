package com.sopt.collaboration.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sopt.collaboration.dto.ReviewResponseDto;
import com.sopt.collaboration.entity.Review;
import com.sopt.collaboration.global.exception.BaseException;
import com.sopt.collaboration.global.response.error.ErrorCode;
import com.sopt.collaboration.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

	private final ReviewRepository reviewRepository;

	@Transactional
	public ReviewResponseDto toggleLike(Long reviewId) {
		// 1. 리뷰 조회
		Review review = reviewRepository.findById(reviewId)
				.orElseThrow(() -> new BaseException(ErrorCode.REVIEW_NOT_FOUND));

		// 2. 좋아요 토글 (Entity 비즈니스 로직 호출)
		review.toggleLike();

		// 3. Entity → Response DTO 변환
		return ReviewResponseDto.from(review);
	}
}
