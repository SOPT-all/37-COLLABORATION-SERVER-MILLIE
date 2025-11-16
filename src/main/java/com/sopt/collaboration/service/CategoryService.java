package com.sopt.collaboration.service;

import com.sopt.collaboration.entity.Category;
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

    // 전체 카테고리 조회
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
