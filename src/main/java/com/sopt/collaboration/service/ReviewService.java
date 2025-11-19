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
		Review review = reviewRepository.findById(reviewId)
				.orElseThrow(() -> new BaseException(ErrorCode.REVIEW_NOT_FOUND));

		review.toggleLike();

		return ReviewResponseDto.from(review);
	}
}
