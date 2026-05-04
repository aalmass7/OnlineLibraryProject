package kz.narxoz.onlinelibrary.controller;

import jakarta.validation.Valid;
import kz.narxoz.onlinelibrary.entity.Category;
import kz.narxoz.onlinelibrary.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService service;

    @GetMapping public List<Category> getAll() { return service.getAll(); }
    @GetMapping("/{id}") public Category getById(@PathVariable Long id) { return service.getById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Category create(@Valid @RequestBody Category category) { return service.create(category); }
    @PutMapping("/{id}") public Category update(@PathVariable Long id, @Valid @RequestBody Category category) { return service.update(id, category); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.delete(id); }
}
