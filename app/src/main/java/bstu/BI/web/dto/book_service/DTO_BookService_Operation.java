package bstu.BI.web.dto.book_service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTO_BookService_Operation {
    @NotNull
    Long bookTypeId;
    @NotNull
    Long amountBooks;
}
