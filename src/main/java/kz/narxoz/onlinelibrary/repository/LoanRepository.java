package kz.narxoz.onlinelibrary.repository;

import kz.narxoz.onlinelibrary.entity.Loan;
import kz.narxoz.onlinelibrary.entity.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByUserId(Long userId);
    List<Loan> findByStatus(LoanStatus status);
}
