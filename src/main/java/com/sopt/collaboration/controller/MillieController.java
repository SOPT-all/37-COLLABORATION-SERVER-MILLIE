package com.sopt.collaboration.controller;


import com.sopt.collaboration.dto.CategoryResponseDto;
import com.sopt.collaboration.global.response.CommonApiResponse;
import com.sopt.collaboration.global.response.success.SuccessCode;
import com.sopt.collaboration.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MillieController {
    private final CategoryService categoryService;

    // 전체 카테고리 조회
    @GetMapping("/categories")
    public CommonApiResponse<List<CategoryResponseDto>> getAllCategories() {
        List<CategoryResponseDto> categories = categoryService.getAllCategories()
                .stream()
                .map(CategoryResponseDto::fromEntity)
                .collect(Collectors.toList());

        return CommonApiResponse.success(SuccessCode.SUCCESS, categories);
    }
}
