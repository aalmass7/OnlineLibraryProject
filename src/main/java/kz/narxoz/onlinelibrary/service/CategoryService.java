package kz.narxoz.onlinelibrary.service;

import kz.narxoz.onlinelibrary.entity.Category;
import kz.narxoz.onlinelibrary.exception.BadRequestException;
import kz.narxoz.onlinelibrary.exception.ResourceNotFoundException;
import kz.narxoz.onlinelibrary.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements CrudService<Category> {
    private final CategoryRepository repository;

    public List<Category> getAll() { return repository.findAll(); }
    public Category getById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id)); }
    public Category create(Category item) {
        if (repository.existsByNameIgnoreCase(item.getName())) throw new BadRequestException("Category already exists");
        item.setId(null);
        return repository.save(item);
    }
    public Category update(Long id, Category item) {
        Category existing = getById(id);
        existing.setName(item.getName());
        existing.setDescription(item.getDescription());
        return repository.save(existing);
    }
    public void delete(Long id) { repository.delete(getById(id)); }
}
