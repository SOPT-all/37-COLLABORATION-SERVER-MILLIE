package com.sopt.collaboration.service;

import com.sopt.collaboration.dto.CategoryResponseDto;
import com.sopt.collaboration.entity.Category;
import com.sopt.collaboration.global.exception.CategoryException;
import com.sopt.collaboration.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sopt.collaboration.global.response.error.ErrorCode.CATEGORY_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponseDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        if (categories.isEmpty()) {
            throw new CategoryException(CATEGORY_NOT_FOUND);
        }

        return categories.stream()
                .map(CategoryResponseDto::fromEntity)
                .toList();
    }
}
