package kz.narxoz.onlinelibrary.repository;

import kz.narxoz.onlinelibrary.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByCategories_NameIgnoreCase(String name);
    List<Book> findByFeaturedTrue();
}
