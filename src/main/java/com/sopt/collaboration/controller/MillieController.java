package com.sopt.collaboration.controller;

import com.sopt.collaboration.dto.CategoryResponseDto;
import com.sopt.collaboration.global.response.CommonApiResponse;
import com.sopt.collaboration.global.response.success.CategorySuccessCode;
import com.sopt.collaboration.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Category", description = "카테고리 조회 API")
public class MillieController {
    private final CategoryService categoryService;

    @Operation(summary = "전체 카테고리 조회", description = "모든 카테고리 정보를 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = CommonApiResponse.class))),
    })
    @GetMapping("/categories")
    public CommonApiResponse<List<CategoryResponseDto>> getAllCategories() {
        List<CategoryResponseDto> categories = categoryService.getAllCategories();
        return CommonApiResponse.success(CategorySuccessCode.CATEGORY_RETRIEVED, categories);
    }
}
