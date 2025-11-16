package com.sopt.collaboration.dto;


import com.sopt.collaboration.entity.Category;
import lombok.Getter;

@Getter
public class CategoryResponseDto {

    private Long categoryId;
    private String title;
    private String description;
    private String imageUrl;

    private CategoryResponseDto(Long categoryId, String title, String description, String imageUrl) {
        this.categoryId = categoryId;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public static CategoryResponseDto fromEntity(Category category) {
        return new CategoryResponseDto(
                category.getCategory_id(),
                category.getTitle(),
                category.getDescription(),
                category.getImage_url()
        );
    }
}
