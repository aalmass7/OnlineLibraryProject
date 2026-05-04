package kz.narxoz.onlinelibrary.controller;

import jakarta.validation.Valid;
import kz.narxoz.onlinelibrary.dto.BookRequest;
import kz.narxoz.onlinelibrary.entity.Book;
import kz.narxoz.onlinelibrary.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService service;

    @GetMapping public List<Book> getAll(@RequestParam(required = false) String title) {
        return title == null || title.isBlank() ? service.getAll() : service.search(title);
    }
    @GetMapping("/{id}") public Book getById(@PathVariable Long id) { return service.getById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Book create(@Valid @RequestBody BookRequest request) { return service.create(request); }
    @PutMapping("/{id}") public Book update(@PathVariable Long id, @Valid @RequestBody BookRequest request) { return service.update(id, request); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.delete(id); }
}
