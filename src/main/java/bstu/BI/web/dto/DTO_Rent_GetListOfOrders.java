package bstu.BI.web.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class DTO_Rent_GetListOfOrders {
    @NotNull
    UserRequisites requisites;
    Integer ticketId;
}
