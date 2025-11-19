package com.sopt.collaboration.repository;

import com.sopt.collaboration.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BannerRepository extends JpaRepository<Banner, Long> {
    Optional<Banner> findFirstByOrderByBannerIdDesc();
}
