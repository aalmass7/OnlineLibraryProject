package kz.narxoz.onlinelibrary.service;

import kz.narxoz.onlinelibrary.entity.Author;
import kz.narxoz.onlinelibrary.exception.ResourceNotFoundException;
import kz.narxoz.onlinelibrary.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService implements CrudService<Author> {
    private final AuthorRepository repository;

    public List<Author> getAll() { return repository.findAll(); }
    public Author getById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author not found: " + id)); }
    public Author create(Author item) { item.setId(null); return repository.save(item); }
    public Author update(Long id, Author item) {
        Author existing = getById(id);
        existing.setName(item.getName());
        existing.setCountry(item.getCountry());
        existing.setBiography(item.getBiography());
        return repository.save(existing);
    }
    public void delete(Long id) { repository.delete(getById(id)); }
}
