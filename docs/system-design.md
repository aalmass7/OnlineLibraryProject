# System Design – BookVerse Online Library

## 1. Architecture Diagram

![Architecture Diagram](UML%20diagrams/architecture-diagram.png)

This diagram shows the layered architecture of the project:
Presentation Layer, Controller Layer, Service Layer, Repository Layer and PostgreSQL database.

---

## 2. UML Class Diagram

![UML Class Diagram](UML%20diagrams/class-diagram.png)

This diagram shows the main Java entities and their relationships:
AppUser, Book, Author, Category, CartItem, Loan and Review.

---

## 3. Database ER Diagram

![Database ER Diagram](UML%20diagrams/database-er-diagram.png)

This diagram shows the PostgreSQL database tables, primary keys, foreign keys and relationships.

The `book_categories` table is used as a join table for the many-to-many relationship between books and categories.

---

## 4. How the System Works

1. A user opens the website or sends a REST API request.
2. The request goes to the controller layer.
3. The controller calls the required service.
4. The service executes business logic.
5. The service uses a repository to access the database.
6. The repository works with PostgreSQL through Spring Data JPA.
7. The result is returned back to the user as a web page or JSON response.

Example:

```text
User → BookController → BookService → BookRepository → PostgreSQL
```

---

## 5. Conclusion

The BookVerse Online Library project includes the required system design diagrams:

- UML Class Diagram
- Database ER Diagram
- Architecture Diagram

The project uses a clear layered architecture and has relationships between database tables.