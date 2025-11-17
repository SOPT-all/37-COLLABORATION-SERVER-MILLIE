package com.sopt.collaboration.repository;

import com.sopt.collaboration.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
