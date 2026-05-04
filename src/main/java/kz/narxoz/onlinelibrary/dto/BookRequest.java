package kz.narxoz.onlinelibrary.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookRequest {
    @NotBlank
    private String title;
    private String isbn;
    @Min(0)
    private int publicationYear;
    @Min(0)
    private int totalCopies;
    @Min(0)
    private int availableCopies;
    private String description;
    private String imageUrl;
    private boolean featured;
    @NotNull
    private Long authorId;
    private List<Long> categoryIds = new ArrayList<>();
}
