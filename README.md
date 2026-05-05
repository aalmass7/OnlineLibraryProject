# BookVerse Online Library

BookVerse Online Library is a Java Spring Boot web application for managing and borrowing books online.

The project allows users to register, log in, browse books, view book details, add books to a cart, borrow books, return borrowed books, and leave reviews.

The application uses PostgreSQL as the main database and follows the standard layered Spring Boot architecture:

```text
Controller → Service → Repository → PostgreSQL
```

---

## Project Theme

The theme of this final project is an online library system.

The main goal of the project is to create a working Java Spring web application with database integration, CRUD functionality, REST API, and clear system architecture.

---

## Main Features

- User registration and login
- Book catalog
- Book search by title
- Category filtering
- Book details page
- Shopping cart for borrowing books
- Book borrowing system
- Returning borrowed books
- Book reviews
- REST API for main resources
- PostgreSQL database integration
- External book cover image links
- Controller → Service → Repository architecture
- System design diagrams

---

## Technologies Used

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- Thymeleaf
- PostgreSQL
- Gradle
- Lombok
- HTML
- CSS

---

## Database

The project uses PostgreSQL as the main database.


Database name:
```text
online_library
```
Before running the project, create the database:

```sql
CREATE DATABASE online_library;
```

Database configuration is located in:

```text
src/main/resources/application.properties
```

Example configuration:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/online_library
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=false
```

---

## Database Tables

The application uses the following PostgreSQL tables:

- users
- authors
- books
- categories
- book_categories
- cart_items
- loans
- reviews

Main relationships:

- One author can have many books.
- One book belongs to one author.
- One book can have many categories.
- One category can contain many books.
- One user can have many cart items.
- One user can have many loans.
- One user can write many reviews.
- One book can have many reviews.
- One book can have many loans.

The `book_categories` table is used as a join table for the many-to-many relationship between books and categories.

---

## Main Entities

The project contains the following main entities:

- AppUser
- Book
- Author
- Category
- CartItem
- Loan
- Review

Additional enums:

- Role
- LoanStatus

This satisfies the requirement of having at least 3–5 entities and relationships between database tables.

---

## Project Architecture

The project follows a layered architecture:

```text
Presentation Layer
        ↓
Controller Layer
        ↓
Service Layer
        ↓
Repository Layer
        ↓
PostgreSQL Database
```

### Controller Layer

Controllers receive HTTP requests and return web pages or API responses.

Main controllers:

- PageController
- AuthController
- BookController
- AuthorController
- CategoryController
- CartController
- LoanController
- ReviewController
- UserController

### Service Layer

Services contain the main business logic of the application.

Main services:

- BookService
- AuthorService
- CategoryService
- CartService
- LoanService
- ReviewService
- UserService

### Repository Layer

Repositories communicate with PostgreSQL through Spring Data JPA.

Main repositories:

- BookRepository
- AuthorRepository
- CategoryRepository
- AppUserRepository
- CartItemRepository
- LoanRepository
- ReviewRepository

---

## REST API

The project includes REST API endpoints for the main resources.

### Books

```text
GET    /api/books
GET    /api/books/{id}
POST   /api/books
PUT    /api/books/{id}
DELETE /api/books/{id}
```

### Authors

```text
GET    /api/authors
GET    /api/authors/{id}
POST   /api/authors
PUT    /api/authors/{id}
DELETE /api/authors/{id}
```

### Categories

```text
GET    /api/categories
GET    /api/categories/{id}
POST   /api/categories
PUT    /api/categories/{id}
DELETE /api/categories/{id}
```

### Reviews

```text
GET    /api/reviews
GET    /api/reviews/{id}
POST   /api/reviews
PUT    /api/reviews/{id}
DELETE /api/reviews/{id}
```

### Loans

```text
GET    /api/loans
GET    /api/loans/{id}
POST   /api/loans/borrow
POST   /api/loans/{id}/return
DELETE /api/loans/{id}
```

### Cart

```text
GET    /api/cart
POST   /api/cart
DELETE /api/cart/{id}
POST   /api/cart/checkout
```

### Users

```text
GET    /api/users
GET    /api/users/{id}
POST   /api/users
PUT    /api/users/{id}
DELETE /api/users/{id}
```

API test examples are located in:

```text
docs/api-tests.http
```

---

## Web Pages

The application also has a Thymeleaf web interface.

Main pages:

```text
/              - Home page and book catalog
/books/{id}    - Book details page
/cart          - User cart
/my-loans      - User borrowed books
/login         - Login page
/register      - Registration page
```

---

## Book Cover Images

Book cover images are stored as external image URLs in the `image_url` column of the `books` table.

Local book image files are not required.  
The application displays book covers directly from external links.

Example:

```text
https://cdn.litres.ru/pub/c/cover/68677652.jpg
```

If the database already contains old image paths, they can be updated with SQL:

```sql
UPDATE public.books
SET image_url = 'https://cdn.litres.ru/pub/c/cover/68677652.jpg'
WHERE title = '1984';
```

---

## Initial Data

The project contains a data initializer that adds sample data when the database is empty.

Sample users:

```text
Admin:
email: admin@library.kz
password: admin123

Reader:
email: reader@library.kz
password: reader123
```

Sample books:

- 1984
- Abai Zholy
- Harry Potter and the Philosopher's Stone
- Atomic Habits
- Sapiens
- Clean Code

---

## System Design Documentation

System design documentation is located in:

```text
docs/system-design.md
```

It includes:

- Architecture Diagram
- UML Class Diagram
- Database ER Diagram

Diagram images are located in:

```text
docs/UMl diagrams/
```

The diagrams show:

- how the application layers are connected;
- how controllers, services, repositories and database work together;
- how Java entities are related;
- how PostgreSQL tables are connected through primary keys and foreign keys.

---

## Project Structure

```text
src/main/java/kz/narxoz/onlinelibrary
│
├── config
│   ├── DataInitializer.java
│   ├── PasswordConfig.java
│   └── SecurityConfig.java
│
├── controller
│   ├── AuthController.java
│   ├── AuthorController.java
│   ├── BookController.java
│   ├── CartController.java
│   ├── CategoryController.java
│   ├── GlobalModelAttributes.java
│   ├── LoanController.java
│   ├── PageController.java
│   ├── ReviewController.java
│   └── UserController.java
│
├── dto
│   ├── BookRequest.java
│   ├── CartItemRequest.java
│   ├── LoanRequest.java
│   ├── RegisterRequest.java
│   └── ReviewRequest.java
│
├── entity
│   ├── AppUser.java
│   ├── Author.java
│   ├── Book.java
│   ├── CartItem.java
│   ├── Category.java
│   ├── Loan.java
│   ├── LoanStatus.java
│   ├── Review.java
│   └── Role.java
│
├── exception
│   ├── BadRequestException.java
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
│
├── repository
│   ├── AppUserRepository.java
│   ├── AuthorRepository.java
│   ├── BookRepository.java
│   ├── CartItemRepository.java
│   ├── CategoryRepository.java
│   ├── LoanRepository.java
│   └── ReviewRepository.java
│
├── service
│   ├── AuthorService.java
│   ├── BookService.java
│   ├── CartService.java
│   ├── CategoryService.java
│   ├── CrudService.java
│   ├── LoanService.java
│   ├── ReviewService.java
│   └── UserService.java
│
└── OnlineLibraryApplication.java
```

---

## How to Run the Project

### 1. Clone the repository

```bash
git clone <repository-url>
cd <project-folder>
```

### 2. Create PostgreSQL database

```sql
CREATE DATABASE online_library;
```

### 3. Configure database connection

Open:

```text
src/main/resources/application.properties
```

Set your PostgreSQL username and password:

```properties
spring.datasource.username=postgres
spring.datasource.password=1234
```

### 4. Run the application

For Windows:

```bash
gradlew.bat bootRun
```

For macOS/Linux:

```bash
./gradlew bootRun
```

The application will start on:

```text
http://localhost:8080
```

---

## How to Test Database Connection

After running the project, open PostgreSQL or IntelliJ Database Console and run:

```sql
SELECT * FROM public.books;
SELECT * FROM public.users;
SELECT * FROM public.loans;
SELECT * FROM public.cart_items;
```

If the data is displayed, the application is connected to PostgreSQL correctly.

---

## Final Project Requirements Coverage

| Requirement | Status |
|---|---|
| Java Spring web application | Done |
| Spring Boot | Done |
| PostgreSQL database | Done |
| REST API | Done |
| CRUD functionality | Done |
| Minimum 3–5 entities | Done |
| Relationships between tables | Done |
| Controller → Service → Repository architecture | Done |
| UML Class Diagram | Done |
| Database ER Diagram | Done |
| Architecture Diagram | Done |
| GitHub repository with commits | Required for submission |

---

## Conclusion

BookVerse Online Library is a complete Java Spring Boot web application with PostgreSQL database integration, REST API, CRUD operations, authentication, book catalog, cart, loans, reviews, and system design documentation.

The project satisfies the main final project requirements for a Java Spring web application.