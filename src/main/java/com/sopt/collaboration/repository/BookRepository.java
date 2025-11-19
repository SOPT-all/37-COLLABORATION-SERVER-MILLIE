package com.sopt.collaboration.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sopt.collaboration.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	@Query("""
		    SELECT b FROM Book b
		    WHERE b.title LIKE %:keyword%
		    ORDER BY 
		        CASE 
		            WHEN b.title = :keyword THEN 1       
		            WHEN b.title LIKE CONCAT(:keyword, '%') THEN 2   
		            ELSE 3                             
		        END,
		        LENGTH(b.title) ASC                   
		""")
	List<Book> findByTitleContainingOrder(@Param("keyword") String keyword);

	@Query("SELECT b FROM Book b LEFT JOIN FETCH b.reviews WHERE b.bookId = :bookId")
	Optional<Book> findByIdWithReviews(@Param("bookId") Long bookId);
}
