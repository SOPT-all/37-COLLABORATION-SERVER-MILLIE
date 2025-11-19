package com.sopt.collaboration.dto;

import com.sopt.collaboration.entity.Banner;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
@Schema(description = "배너 정보 DTO")
public class BannerDto {

	@Schema(description = "배너 ID", example = "10")
	private Long bannerId;

	@Schema(description = "배너 제목", example = "《홍학의 자리》 읽을 준비!")
	private String bannerTitle;

	@Schema(description = "배너 내용", example = "이 책부터 읽어야 재미가 2배")
	private String bannerContent;

	@Schema(description = "배너 이미지 URL", example = "https://image.millie.co.kr/banners/5678.jpg")
	private String bannerImageUrl;

	public static BannerDto from(Banner banner, String imageUrl) {
		if (banner == null)
			return null;
		return BannerDto.builder()
			.bannerId(banner.getBannerId())
			.bannerTitle(banner.getTitle())
			.bannerContent(banner.getContent())
			.bannerImageUrl(imageUrl)
			.build();
	}
}
