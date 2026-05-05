package kz.narxoz.onlinelibrary.controller;

import kz.narxoz.onlinelibrary.dto.CartItemRequest;
import kz.narxoz.onlinelibrary.entity.CartItem;
import kz.narxoz.onlinelibrary.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long bookId, RedirectAttributes redirectAttributes) {
        cartService.addToCart(bookId);
        redirectAttributes.addFlashAttribute("successMessage", "Book added to cart");
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        cartService.removeItem(id);
        redirectAttributes.addFlashAttribute("successMessage", "Item removed from cart");
        return "redirect:/cart";
    }

    @PostMapping("/cart/checkout")
    public String checkout(@RequestParam(defaultValue = "14") int days, RedirectAttributes redirectAttributes) {
        int count = cartService.checkout(days);
        redirectAttributes.addFlashAttribute("successMessage", count + " book(s) successfully borrowed");
        return "redirect:/my-loans";
    }

    @GetMapping("/api/cart")
    @ResponseBody
    public List<CartItem> getCartApi() {
        return cartService.getCurrentUserCart();
    }

    @PostMapping("/api/cart")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addToCartApi(@RequestBody CartItemRequest request) {
        cartService.addToCart(request.getBookId(), request.getQuantity());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Added to cart"));
    }

    @DeleteMapping("/api/cart/{id}")
    @ResponseBody
    public ResponseEntity<Void> removeFromCartApi(@PathVariable Long id) {
        cartService.removeItem(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/cart/checkout")
    @ResponseBody
    public Map<String, Object> checkoutApi(@RequestParam(defaultValue = "14") int days) {
        return Map.of("borrowedCount", cartService.checkout(days), "message", "Checkout completed");
    }
}
