package kz.narxoz.onlinelibrary.service;

import kz.narxoz.onlinelibrary.dto.LoanRequest;
import kz.narxoz.onlinelibrary.entity.AppUser;
import kz.narxoz.onlinelibrary.entity.Book;
import kz.narxoz.onlinelibrary.entity.Loan;
import kz.narxoz.onlinelibrary.entity.LoanStatus;
import kz.narxoz.onlinelibrary.exception.BadRequestException;
import kz.narxoz.onlinelibrary.exception.ResourceNotFoundException;
import kz.narxoz.onlinelibrary.repository.AppUserRepository;
import kz.narxoz.onlinelibrary.repository.BookRepository;
import kz.narxoz.onlinelibrary.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final LoanRepository loanRepository;
    private final AppUserRepository userRepository;
    private final BookRepository bookRepository;

    public List<Loan> getAll() { return loanRepository.findAll(); }
    public Loan getById(Long id) { return loanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Loan not found: " + id)); }
    public List<Loan> getByUser(Long userId) { return loanRepository.findByUserId(userId); }

    @Transactional
    public Loan borrow(LoanRequest request) {
        AppUser user = userRepository.findById(request.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found: " + request.getUserId()));
        Book book = bookRepository.findById(request.getBookId()).orElseThrow(() -> new ResourceNotFoundException("Book not found: " + request.getBookId()));
        if (book.getAvailableCopies() <= 0) throw new BadRequestException("No available copies for this book");
        int days = request.getDays() == null ? 14 : request.getDays();
        if (days < 1 || days > 60) throw new BadRequestException("Borrow period must be between 1 and 60 days");
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);
        Loan loan = Loan.builder()
                .user(user)
                .book(book)
                .borrowedAt(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(days))
                .status(LoanStatus.ACTIVE)
                .build();
        return loanRepository.save(loan);
    }

    @Transactional
    public Loan returnBook(Long loanId) {
        Loan loan = getById(loanId);
        if (loan.getStatus() == LoanStatus.RETURNED) throw new BadRequestException("Loan already returned");
        Book book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);
        loan.setReturnedAt(LocalDate.now());
        loan.setStatus(LoanStatus.RETURNED);
        return loanRepository.save(loan);
    }

    public void delete(Long id) { loanRepository.delete(getById(id)); }
}
