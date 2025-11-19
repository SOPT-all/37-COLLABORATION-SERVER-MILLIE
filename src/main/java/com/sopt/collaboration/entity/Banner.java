package com.sopt.collaboration.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Banner")
public class Banner {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bannerId;

	@Column(nullable = false, length = 20)
	private String title;

	@Column(nullable = false, length = 20)
	private String content;

	@Column(name = "banner_image_key")
	private String bannerImageKey;

	@OneToOne
	@JoinColumn(name = "book_id", nullable = false)
	private Book book;

	@Builder
	public Banner(String title, String content, String bannerImageKey, Book book) {
		this.title = title;
		this.content = content;
		this.bannerImageKey = bannerImageKey;
		this.book = book;
	}
}
