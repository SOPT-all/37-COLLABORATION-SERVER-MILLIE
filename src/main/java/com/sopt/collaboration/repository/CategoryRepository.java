package com.sopt.collaboration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sopt.collaboration.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
