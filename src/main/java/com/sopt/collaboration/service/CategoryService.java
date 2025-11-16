package com.sopt.collaboration.service;

import com.sopt.collaboration.entity.Category;
import com.sopt.collaboration.global.exception.CategoryException;
import com.sopt.collaboration.global.response.error.CategoryErrorCode;
import com.sopt.collaboration.repository.CategoryRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        if (categories.isEmpty()) {
            throw new CategoryException(CategoryErrorCode.CATEGORY_NOT_FOUND);
        }

        return categories;
    }

}
