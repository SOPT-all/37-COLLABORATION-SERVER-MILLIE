package com.sopt.collaboration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sopt.collaboration.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
