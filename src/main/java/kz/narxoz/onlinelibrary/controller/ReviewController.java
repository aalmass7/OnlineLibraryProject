package kz.narxoz.onlinelibrary.controller;

import jakarta.validation.Valid;
import kz.narxoz.onlinelibrary.dto.ReviewRequest;
import kz.narxoz.onlinelibrary.entity.Review;
import kz.narxoz.onlinelibrary.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService service;

    @GetMapping public List<Review> getAll(@RequestParam(required = false) Long bookId) {
        return bookId == null ? service.getAll() : service.getByBook(bookId);
    }
    @GetMapping("/{id}") public Review getById(@PathVariable Long id) { return service.getById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Review create(@Valid @RequestBody ReviewRequest request) { return service.create(request); }
    @PutMapping("/{id}") public Review update(@PathVariable Long id, @Valid @RequestBody ReviewRequest request) { return service.update(id, request); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.delete(id); }
}
