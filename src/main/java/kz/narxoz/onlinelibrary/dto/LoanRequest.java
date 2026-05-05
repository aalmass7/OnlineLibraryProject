package kz.narxoz.onlinelibrary.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoanRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long bookId;
    private Integer days = 14;
}
