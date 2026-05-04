package kz.narxoz.onlinelibrary.controller;

import jakarta.validation.Valid;
import kz.narxoz.onlinelibrary.entity.Author;
import kz.narxoz.onlinelibrary.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService service;

    @GetMapping public List<Author> getAll() { return service.getAll(); }
    @GetMapping("/{id}") public Author getById(@PathVariable Long id) { return service.getById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Author create(@Valid @RequestBody Author author) { return service.create(author); }
    @PutMapping("/{id}") public Author update(@PathVariable Long id, @Valid @RequestBody Author author) { return service.update(id, author); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.delete(id); }
}
