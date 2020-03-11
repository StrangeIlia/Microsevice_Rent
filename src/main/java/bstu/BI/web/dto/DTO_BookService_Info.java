package bstu.BI.web.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

//@Getter
@Data
public class DTO_BookService_Info {
    @NotNull
    private Double rentPrice;
    @NotNull
    private Double purchasePrice;
}
