package com.sopt.collaboration.service;

import com.sopt.collaboration.dto.BookSearchResponseDto;
import com.sopt.collaboration.entity.Banner;
import com.sopt.collaboration.entity.Book;
import com.sopt.collaboration.global.exception.BaseException;
import com.sopt.collaboration.global.response.error.ErrorCode;
import com.sopt.collaboration.global.s3.service.S3Service;
import com.sopt.collaboration.repository.BannerRepository;
import com.sopt.collaboration.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BannerRepository bannerRepository;
    private final S3Service s3Service;

    public BookSearchResponseDto searchBooks(String keyword) {

        // 1. 키워드로 책 검색
        List<Book> books = bookRepository.findByTitleContaining(keyword);

        if (books.isEmpty()) {
            throw new BaseException(ErrorCode.BOOK_NOT_FOUND);
        }

        // 2. 책 각각의 이미지
        Map<Long, String> bookImageMap = books.stream()
                .collect(Collectors.toMap(
                        Book::getBookId,
                        book -> s3Service.generatePresignedUrlOrNull(book.getBookImageKey())
                ));

        // 3. 배너 조회
        Banner banner = bannerRepository.findFirstByOrderByBannerIdDesc().orElse(null);

        String bannerImageUrl = (banner != null)
                ? s3Service.generatePresignedUrlOrNull(banner.getBannerImageKey())
                : null;

        return BookSearchResponseDto.from(
                keyword,
                books,
                banner,
                bookImageMap,
                bannerImageUrl
        );
    }
}




