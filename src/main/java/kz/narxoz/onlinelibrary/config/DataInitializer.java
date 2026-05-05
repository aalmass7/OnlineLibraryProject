package kz.narxoz.onlinelibrary.config;

import kz.narxoz.onlinelibrary.entity.*;
import kz.narxoz.onlinelibrary.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final AppUserRepository userRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final ReviewRepository reviewRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) return;

        AppUser admin = userRepository.save(AppUser.builder()
                .fullName("Admin User")
                .email("admin@library.kz")
                .password(passwordEncoder.encode("admin123"))
                .role(Role.ADMIN)
                .enabled(true)
                .build());

        AppUser reader = userRepository.save(AppUser.builder()
                .fullName("Aruzhan Reader")
                .email("reader@library.kz")
                .password(passwordEncoder.encode("reader123"))
                .role(Role.READER)
                .enabled(true)
                .build());

        Author orwell = authorRepository.save(Author.builder().name("George Orwell").country("United Kingdom").biography("Author of dystopian novels and essays about society, power and truth.").build());
        Author auezov = authorRepository.save(Author.builder().name("Mukhtar Auezov").country("Kazakhstan").biography("Kazakh writer, playwright and public figure, best known for the epic novel Abai Zholy.").build());
        Author rowling = authorRepository.save(Author.builder().name("J. K. Rowling").country("United Kingdom").biography("Fantasy writer, creator of the Harry Potter universe.").build());
        Author clear = authorRepository.save(Author.builder().name("James Clear").country("United States").biography("Writer focused on habits, self-improvement and productivity.").build());
        Author harari = authorRepository.save(Author.builder().name("Yuval Noah Harari").country("Israel").biography("Historian and author of popular non-fiction books about humankind and civilization.").build());
        Author martin = authorRepository.save(Author.builder().name("Robert C. Martin").country("United States").biography("Software engineer and author known for writing about clean code and software craftsmanship.").build());

        Category fiction = categoryRepository.save(Category.builder().name("Fiction").description("Novels and classic stories").build());
        Category history = categoryRepository.save(Category.builder().name("History").description("Historical literature and civilization").build());
        Category fantasy = categoryRepository.save(Category.builder().name("Fantasy").description("Fantasy worlds and magic").build());
        Category selfHelp = categoryRepository.save(Category.builder().name("Self-Help").description("Personal growth and habits").build());
        Category technology = categoryRepository.save(Category.builder().name("Technology").description("Programming and software engineering").build());
        Category popular = categoryRepository.save(Category.builder().name("Popular").description("Trending and recommended books").build());

        Book b1 = bookRepository.save(Book.builder()
                .title("1984")
                .isbn("9780451524935")
                .publicationYear(1949)
                .totalCopies(5)
                .availableCopies(4)
                .description("Dystopian social science fiction novel about surveillance, truth and personal freedom.")
                .imageUrl("https://cdn.litres.ru/pub/c/cover/68677652.jpg")
                .featured(true)
                .author(orwell)
                .categories(List.of(fiction, popular))
                .build());

        bookRepository.save(Book.builder()
                .title("Abai Zholy")
                .isbn("9786012926847")
                .publicationYear(1942)
                .totalCopies(3)
                .availableCopies(3)
                .description("Epic novel about the life, environment and historical period of Abai Kunanbayev.")
                .imageUrl("https://www.abaicenter.org/wp-content/uploads/2022/10/5sidorkin_1_11zon.jpg")
                .featured(true)
                .author(auezov)
                .categories(List.of(fiction, history, popular))
                .build());

        bookRepository.save(Book.builder()
                .title("Harry Potter and the Philosopher's Stone")
                .isbn("9780747532699")
                .publicationYear(1997)
                .totalCopies(7)
                .availableCopies(7)
                .description("Fantasy novel about a young wizard beginning his magical journey at Hogwarts.")
                .imageUrl("https://bci.kinokuniya.com/ae/jsp/images/book-img/97814/97814088/9781408855652.JPG")
                .featured(true)
                .author(rowling)
                .categories(List.of(fantasy, fiction, popular))
                .build());

        bookRepository.save(Book.builder()
                .title("Atomic Habits")
                .isbn("9780735211292")
                .publicationYear(2018)
                .totalCopies(6)
                .availableCopies(6)
                .description("A practical guide to building good habits, breaking bad ones and improving every day.")
                .imageUrl("https://m.media-amazon.com/images/I/712Uo++xK2L._AC_UF1000,1000_QL80_.jpg")
                .featured(true)
                .author(clear)
                .categories(List.of(selfHelp, popular))
                .build());

        bookRepository.save(Book.builder()
                .title("Sapiens")
                .isbn("9780062316097")
                .publicationYear(2011)
                .totalCopies(4)
                .availableCopies(4)
                .description("A brief history of humankind from ancient communities to modern civilization.")
                .imageUrl("https://m.media-amazon.com/images/I/81Rnac2Fq+L._AC_UF1000,1000_QL80_.jpg")
                .featured(true)
                .author(harari)
                .categories(List.of(history, popular))
                .build());

        bookRepository.save(Book.builder()
                .title("Clean Code")
                .isbn("9780132350884")
                .publicationYear(2008)
                .totalCopies(5)
                .availableCopies(5)
                .description("A handbook of agile software craftsmanship and writing readable, maintainable code.")
                .imageUrl("https://m.media-amazon.com/images/I/81kg51XRc1L._AC_UF1000,1000_QL80_.jpg")
                .featured(true)
                .author(martin)
                .categories(List.of(technology, popular))
                .build());

        loanRepository.save(Loan.builder()
                .user(reader)
                .book(b1)
                .borrowedAt(LocalDate.now().minusDays(2))
                .dueDate(LocalDate.now().plusDays(12))
                .status(LoanStatus.ACTIVE)
                .build());

        reviewRepository.save(Review.builder()
                .book(b1)
                .user(reader)
                .rating(5)
                .comment("Very interesting and still relevant.")
                .createdAt(LocalDateTime.now())
                .build());

        reviewRepository.save(Review.builder()
                .book(b1)
                .user(admin)
                .rating(4)
                .comment("Strong recommendation for dystopian fiction fans.")
                .createdAt(LocalDateTime.now().minusHours(1))
                .build());
    }
}
