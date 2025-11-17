package com.sopt.collaboration.controller;

import static com.sopt.collaboration.global.response.success.SuccessCode.*;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sopt.collaboration.dto.CategoryResponseDto;
import com.sopt.collaboration.dto.ReviewResponseDto;
import com.sopt.collaboration.global.response.CommonApiResponse;
import com.sopt.collaboration.global.response.success.SuccessCode;
import com.sopt.collaboration.service.CategoryService;
import com.sopt.collaboration.service.ReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Category", description = "카테고리 조회 API")
public class MillieController {

    private final CategoryService categoryService;
    private final ReviewService reviewService;

    @Operation(summary = "전체 카테고리 조회", description = "모든 카테고리 정보를 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = CommonApiResponse.class))),
    })
    @GetMapping("/categories")
    public CommonApiResponse<List<CategoryResponseDto>> getAllCategories() {
        List<CategoryResponseDto> categories = categoryService.getAllCategories();
        return CommonApiResponse.success(CATEGORY_RETRIEVED, categories);
    }

    @Operation(summary = "리뷰 좋아요 토글", description = "리뷰의 좋아요를 토글합니다. 좋아요가 되어있으면 취소, 안되어있으면 좋아요 처리됩니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "좋아요 토글 성공"),
        @ApiResponse(responseCode = "404", description = "리뷰를 찾을 수 없음")
    })
    @PostMapping("/reviews/{reviewId}/like")
    public CommonApiResponse<ReviewResponseDto> toggleLike(
        @Parameter(description = "리뷰 ID", required = true) @PathVariable Long reviewId) {
        ReviewResponseDto review = reviewService.toggleLike(reviewId);
        return CommonApiResponse.success(SuccessCode.REVIEW_LIKE_TOGGLED, review);
    }
}
