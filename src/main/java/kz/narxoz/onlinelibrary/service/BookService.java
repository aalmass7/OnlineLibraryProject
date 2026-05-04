package kz.narxoz.onlinelibrary.service;

import kz.narxoz.onlinelibrary.dto.BookRequest;
import kz.narxoz.onlinelibrary.entity.Author;
import kz.narxoz.onlinelibrary.entity.Book;
import kz.narxoz.onlinelibrary.entity.Category;
import kz.narxoz.onlinelibrary.exception.BadRequestException;
import kz.narxoz.onlinelibrary.exception.ResourceNotFoundException;
import kz.narxoz.onlinelibrary.repository.AuthorRepository;
import kz.narxoz.onlinelibrary.repository.BookRepository;
import kz.narxoz.onlinelibrary.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    public List<Book> getAll() { return bookRepository.findAll(); }
    public List<Book> search(String title) { return bookRepository.findByTitleContainingIgnoreCase(title); }
    public List<Book> getFeaturedBooks() { return bookRepository.findByFeaturedTrue(); }
    public List<Book> getByCategory(String categoryName) { return bookRepository.findByCategories_NameIgnoreCase(categoryName); }
    public Book getById(Long id) { return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found: " + id)); }

    public Book create(BookRequest request) {
        validateCopies(request.getTotalCopies(), request.getAvailableCopies());
        Book book = mapRequest(new Book(), request);
        return bookRepository.save(book);
    }

    public Book update(Long id, BookRequest request) {
        validateCopies(request.getTotalCopies(), request.getAvailableCopies());
        Book existing = getById(id);
        return bookRepository.save(mapRequest(existing, request));
    }

    public void delete(Long id) { bookRepository.delete(getById(id)); }

    private Book mapRequest(Book book, BookRequest request) {
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found: " + request.getAuthorId()));
        List<Category> categories = categoryRepository.findAllById(request.getCategoryIds());
        if (categories.size() != request.getCategoryIds().size()) throw new ResourceNotFoundException("One or more categories not found");
        book.setTitle(request.getTitle());
        book.setIsbn(request.getIsbn());
        book.setPublicationYear(request.getPublicationYear());
        book.setTotalCopies(request.getTotalCopies());
        book.setAvailableCopies(request.getAvailableCopies());
        book.setDescription(request.getDescription());
        book.setImageUrl(request.getImageUrl());
        book.setFeatured(request.isFeatured());
        book.setAuthor(author);
        book.setCategories(categories);
        return book;
    }

    private void validateCopies(int total, int available) {
        if (available > total) throw new BadRequestException("Available copies cannot be greater than total copies");
    }
}
