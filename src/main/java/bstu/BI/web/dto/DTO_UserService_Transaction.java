package bstu.BI.web.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DTO_UserService_Transaction {
    @NotNull
    UserRequisites requisites;
    @NotNull
    Double cost;
}
