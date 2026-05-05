package kz.narxoz.onlinelibrary.controller;

import jakarta.validation.Valid;
import kz.narxoz.onlinelibrary.dto.LoanRequest;
import kz.narxoz.onlinelibrary.entity.Loan;
import kz.narxoz.onlinelibrary.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {
    private final LoanService service;

    @GetMapping public List<Loan> getAll(@RequestParam(required = false) Long userId) {
        return userId == null ? service.getAll() : service.getByUser(userId);
    }
    @GetMapping("/{id}") public Loan getById(@PathVariable Long id) { return service.getById(id); }
    @PostMapping("/borrow") @ResponseStatus(HttpStatus.CREATED) public Loan borrow(@Valid @RequestBody LoanRequest request) { return service.borrow(request); }
    @PostMapping("/{id}/return") public Loan returnBook(@PathVariable Long id) { return service.returnBook(id); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.delete(id); }
}
