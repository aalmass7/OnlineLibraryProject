package kz.narxoz.onlinelibrary.controller;

import kz.narxoz.onlinelibrary.service.BookService;
import kz.narxoz.onlinelibrary.service.CartService;
import kz.narxoz.onlinelibrary.service.CategoryService;
import kz.narxoz.onlinelibrary.service.LoanService;
import kz.narxoz.onlinelibrary.service.ReviewService;
import kz.narxoz.onlinelibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PageController {
    private final BookService bookService;
    private final CategoryService categoryService;
    private final ReviewService reviewService;
    private final CartService cartService;
    private final LoanService loanService;
    private final UserService userService;

    @GetMapping("/")
    public String home(@RequestParam(required = false) String q,
                       @RequestParam(required = false) String genre,
                       Model model) {
        model.addAttribute("books",
                q != null && !q.isBlank() ? bookService.search(q) :
                        genre != null && !genre.isBlank() ? bookService.getByCategory(genre) :
                                bookService.getAll());
        model.addAttribute("featuredBooks", bookService.getFeaturedBooks());
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("q", q);
        model.addAttribute("selectedGenre", genre);
        return "index";
    }

    @GetMapping("/books/{id}")
    public String bookDetails(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.getById(id));
        model.addAttribute("reviews", reviewService.getByBook(id));
        return "book-details";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("cartItems", cartService.getCurrentUserCart());
        return "cart";
    }

    @GetMapping("/my-loans")
    public String myLoans(Model model) {
        var currentUser = userService.getCurrentUser();
        model.addAttribute("loans", loanService.getByUser(currentUser.getId()));
        return "my-loans";
    }

    @GetMapping("/login")
    public String login() { return "login"; }

    @GetMapping("/register")
    public String register() { return "register"; }
}
