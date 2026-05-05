package kz.narxoz.onlinelibrary.service;

import kz.narxoz.onlinelibrary.dto.LoanRequest;
import kz.narxoz.onlinelibrary.entity.AppUser;
import kz.narxoz.onlinelibrary.entity.Book;
import kz.narxoz.onlinelibrary.entity.CartItem;
import kz.narxoz.onlinelibrary.exception.BadRequestException;
import kz.narxoz.onlinelibrary.exception.ResourceNotFoundException;
import kz.narxoz.onlinelibrary.repository.BookRepository;
import kz.narxoz.onlinelibrary.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final UserService userService;
    private final LoanService loanService;

    public List<CartItem> getCurrentUserCart() {
        AppUser user = userService.getCurrentUser();
        return cartItemRepository.findByUserId(user.getId());
    }

    public long getCurrentUserCartCount() {
        AppUser user = userService.getCurrentUserOrNull();
        return user == null ? 0 : cartItemRepository.countByUserId(user.getId());
    }

    @Transactional
    public void addToCart(Long bookId) {
        addToCart(bookId, 1);
    }

    @Transactional
    public void addToCart(Long bookId, int quantity) {
        if (quantity < 1) {
            throw new BadRequestException("Quantity must be at least 1");
        }
        AppUser user = userService.getCurrentUser();
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + bookId));

        if (book.getAvailableCopies() <= 0) {
            throw new BadRequestException("This book is currently unavailable");
        }

        cartItemRepository.findByUserIdAndBookId(user.getId(), bookId)
                .ifPresentOrElse(item -> {
                    int newQuantity = item.getQuantity() + quantity;
                    if (newQuantity > book.getAvailableCopies()) {
                        throw new BadRequestException("Requested quantity is greater than available copies");
                    }
                    item.setQuantity(newQuantity);
                    cartItemRepository.save(item);
                }, () -> {
                    if (quantity > book.getAvailableCopies()) {
                        throw new BadRequestException("Requested quantity is greater than available copies");
                    }
                    cartItemRepository.save(CartItem.builder()
                            .user(user)
                            .book(book)
                            .quantity(quantity)
                            .build());
                });
    }

    public void removeItem(Long cartItemId) {
        AppUser user = userService.getCurrentUser();
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found: " + cartItemId));
        if (!item.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("You cannot modify another user's cart");
        }
        cartItemRepository.delete(item);
    }

    public void clearCurrentUserCart() {
        AppUser user = userService.getCurrentUser();
        cartItemRepository.deleteByUserId(user.getId());
    }

    @Transactional
    public int checkout(int borrowDays) {
        AppUser user = userService.getCurrentUser();
        List<CartItem> items = cartItemRepository.findByUserId(user.getId());
        if (items.isEmpty()) {
            throw new BadRequestException("Cart is empty");
        }
        int count = 0;
        for (CartItem item : items) {
            for (int i = 0; i < item.getQuantity(); i++) {
                LoanRequest request = new LoanRequest();
                request.setUserId(user.getId());
                request.setBookId(item.getBook().getId());
                request.setDays(borrowDays);
                loanService.borrow(request);
                count++;
            }
        }
        cartItemRepository.deleteByUserId(user.getId());
        return count;
    }
}
