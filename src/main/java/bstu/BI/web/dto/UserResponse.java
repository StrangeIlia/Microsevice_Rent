package bstu.BI.web.dto;

import bstu.BI.entity.enums.Status;
import lombok.Data;

@Data
public class UserResponse {
    private Status status;
    private String explanation;

    UserResponse(Status status) {
        this.status = status;
    }

    public static UserResponse success() {
        return new UserResponse(Status.SUCCESS);
    }

    public static UserResponse error(String explanation) {
        UserResponse response = new UserResponse(Status.FAIL);
        response.setExplanation(explanation);
        return response;
    }
}
