package kz.narxoz.onlinelibrary.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long bookId;
    @Min(1)
    @Max(5)
    private int rating;
    @NotBlank
    private String comment;
}
