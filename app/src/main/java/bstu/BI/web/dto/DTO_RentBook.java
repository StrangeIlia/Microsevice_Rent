package bstu.BI.web.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTO_RentBook {
    @NotNull
    Long bookTypeId;
    @NotNull
    Long amountBooks;
}
