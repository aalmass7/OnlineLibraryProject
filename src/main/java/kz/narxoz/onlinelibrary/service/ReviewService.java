package kz.narxoz.onlinelibrary.service;

import kz.narxoz.onlinelibrary.dto.ReviewRequest;
import kz.narxoz.onlinelibrary.entity.AppUser;
import kz.narxoz.onlinelibrary.entity.Book;
import kz.narxoz.onlinelibrary.entity.Review;
import kz.narxoz.onlinelibrary.exception.ResourceNotFoundException;
import kz.narxoz.onlinelibrary.repository.AppUserRepository;
import kz.narxoz.onlinelibrary.repository.BookRepository;
import kz.narxoz.onlinelibrary.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final AppUserRepository userRepository;
    private final BookRepository bookRepository;

    public List<Review> getAll() { return reviewRepository.findAll(); }
    public List<Review> getByBook(Long bookId) { return reviewRepository.findByBookId(bookId); }
    public Review getById(Long id) { return reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Review not found: " + id)); }

    public Review create(ReviewRequest request) {
        AppUser user = userRepository.findById(request.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found: " + request.getUserId()));
        Book book = bookRepository.findById(request.getBookId()).orElseThrow(() -> new ResourceNotFoundException("Book not found: " + request.getBookId()));
        return reviewRepository.save(Review.builder()
                .user(user).book(book).rating(request.getRating())
                .comment(request.getComment()).createdAt(LocalDateTime.now()).build());
    }

    public Review update(Long id, ReviewRequest request) {
        Review review = getById(id);
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        return reviewRepository.save(review);
    }

    public void delete(Long id) { reviewRepository.delete(getById(id)); }
}
