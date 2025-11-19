package com.sopt.collaboration.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sopt.collaboration.dto.CategoryResponseDto;
import com.sopt.collaboration.entity.Category;
import com.sopt.collaboration.global.exception.BaseException;
import com.sopt.collaboration.global.response.error.ErrorCode;
import com.sopt.collaboration.global.s3.service.S3Service;
import com.sopt.collaboration.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepository categoryRepository;
	private final S3Service s3Service;

	public List<CategoryResponseDto> getAllCategories() {
		List<Category> categories = categoryRepository.findAll();

		if (categories.isEmpty()) {
			throw new BaseException(ErrorCode.CATEGORY_NOT_FOUND);
		}

		return categories.stream()
			.map(category -> {
				String categoryImageUrl = s3Service.generatePresignedUrlOrNull(category.getCategoryImageKey());
				return CategoryResponseDto.of(category, categoryImageUrl);
			})
			.toList();
	}
}
