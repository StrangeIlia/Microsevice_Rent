package bstu.BI.web.dto.rent_service;

import bstu.BI.web.dto.user_service.DTO_UserService_UserRequisites;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
public class DTO_RentService_CreateOrder {
    @NotNull
    DTO_UserService_UserRequisites requisites;
    @NotNull
    Long bookTypeId;
    @NotNull
    Date rentalFinish;
}
