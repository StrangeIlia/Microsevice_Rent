package bstu.BI.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class DTO_RentBook {
    @NotNull
    Long bookTypeId;
    @NotNull
    Long amountBooks;

    public static DTO_RentBook buyOne(@NotNull Long bookTypeId) {
        return new DTO_RentBook(bookTypeId, -1L);
    }

    public static DTO_RentBook returnOne(@NotNull Long bookTypeId) {
        return new DTO_RentBook(bookTypeId, 1L);
    }
}
