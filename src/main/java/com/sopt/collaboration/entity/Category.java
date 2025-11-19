package com.sopt.collaboration.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Category")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categoryId;

	@Column(nullable = false, length = 20)
	private String title;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String description;

	@Column(name = "category_image_key")
	private String categoryImageKey;

	@Builder
	private Category(String title, String description, String categoryImageKey) {
		this.title = title;
		this.description = description;
		this.categoryImageKey = categoryImageKey;
	}
}
