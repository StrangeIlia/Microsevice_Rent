package bstu.BI.web.dto.book_service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

//@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTO_BookService_Response {
    @NotNull
    private Double rentPrice;
    @NotNull
    private Double purchasePrice;
}
