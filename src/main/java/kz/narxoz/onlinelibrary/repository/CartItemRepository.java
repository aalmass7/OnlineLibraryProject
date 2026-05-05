package kz.narxoz.onlinelibrary.repository;

import kz.narxoz.onlinelibrary.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUserId(Long userId);
    Optional<CartItem> findByUserIdAndBookId(Long userId, Long bookId);
    long countByUserId(Long userId);
    void deleteByUserId(Long userId);
}
