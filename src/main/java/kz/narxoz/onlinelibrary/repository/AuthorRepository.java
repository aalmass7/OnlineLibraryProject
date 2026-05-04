package kz.narxoz.onlinelibrary.repository;

import kz.narxoz.onlinelibrary.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
