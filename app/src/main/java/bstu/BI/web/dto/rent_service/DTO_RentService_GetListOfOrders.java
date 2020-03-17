package bstu.BI.web.dto.rent_service;

import bstu.BI.web.dto.user_service.DTO_UserService_UserRequisites;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class DTO_RentService_GetListOfOrders {
    @NotNull
    DTO_UserService_UserRequisites requisites;
    Integer ticketId;
}
