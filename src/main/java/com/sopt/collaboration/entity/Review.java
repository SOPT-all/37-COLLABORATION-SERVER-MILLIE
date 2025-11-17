package com.sopt.collaboration.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long bookId; // 추후 Book 엔티티와 연관관계 설정 예정

	@Column(nullable = false)
	private String reviewerName;

	@Column(nullable = false)
	private String createdDate;

	@Column(nullable = false)
	private String content;

	@Column(nullable = false)
	private int likeCount;

	@Column(nullable = false)
	private boolean isLiked;

	@Builder
	private Review(Long bookId, String reviewerName, String createdDate, String content) {
		this.bookId = bookId;
		this.reviewerName = reviewerName;
		this.createdDate = createdDate;
		this.content = content;
		this.likeCount = 0;
		this.isLiked = false;
	}

	public void toggleLike() {
		int delta = this.isLiked ? -1 : 1;
		this.likeCount = Math.max(0, this.likeCount + delta);
		this.isLiked = !this.isLiked;
	}

}
