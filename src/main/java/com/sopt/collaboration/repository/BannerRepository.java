package com.sopt.collaboration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sopt.collaboration.entity.Banner;

public interface BannerRepository extends JpaRepository<Banner, Long> {
	Optional<Banner> findFirstByOrderByBannerIdDesc();
}
