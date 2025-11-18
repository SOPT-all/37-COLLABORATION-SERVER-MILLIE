package com.sopt.collaboration.entity;

import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

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
	private Review(Book book, String reviewerName, String createdDate, String content) {
		this.book = book;
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
