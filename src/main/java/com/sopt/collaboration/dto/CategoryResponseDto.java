package com.sopt.collaboration.dto;

import com.sopt.collaboration.entity.Category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(name = "CategoryResponseDto", description = "카테고리 응답 DTO")
public class CategoryResponseDto {

	@Schema(description = "카테고리 ID", example = "1")
	private Long categoryId;

	@Schema(description = "카테고리 제목", example = "소설")
	private String title;

	@Schema(description = "카테고리 설명", example = "추리/스릴러,킬러 스파이,법의학 스릴러,SF")
	private String description;

	@Schema(description = "카테고리 이미지 URL", example = "s3://millie-collaboration-bucket/images/category-images/CATEGORY-IMAGE-NOVEL.png")
	private String imageUrl;

	public static CategoryResponseDto of(Category category, String imageUrl) {
		return CategoryResponseDto.builder()
			.categoryId(category.getCategoryId())
			.title(category.getTitle())
			.description(category.getDescription())
			.imageUrl(imageUrl)
			.build();
	}
}
