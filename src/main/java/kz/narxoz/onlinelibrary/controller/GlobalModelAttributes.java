package kz.narxoz.onlinelibrary.controller;

import kz.narxoz.onlinelibrary.service.CartService;
import kz.narxoz.onlinelibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAttributes {
    private final UserService userService;
    private final CartService cartService;

    @ModelAttribute("currentUser")
    public Object currentUser() {
        return userService.getCurrentUserOrNull();
    }

    @ModelAttribute("isAuthenticated")
    public boolean isAuthenticated() {
        return userService.getCurrentUserOrNull() != null;
    }

    @ModelAttribute("cartCount")
    public long cartCount() {
        return cartService.getCurrentUserCartCount();
    }
}
