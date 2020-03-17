package bstu.BI.web.dto.user_service;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DTO_UserService_Transaction {
    @NotNull
    DTO_UserService_UserRequisites requisites;
    @NotNull
    Double cost;
}
