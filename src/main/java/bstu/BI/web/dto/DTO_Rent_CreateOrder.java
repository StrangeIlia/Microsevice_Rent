package bstu.BI.web.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
public class DTO_Rent_CreateOrder {
    @NotNull
    UserRequisites requisites;
    @NotNull
    Integer bookTypeId;
    @NotNull
    Date rentalFinish;
}
