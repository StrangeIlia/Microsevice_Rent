package bstu.BI.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private String explanation;

    public static UserResponse success() {
        return new UserResponse(null);
    }

    public static UserResponse error(String explanation) {
        return new UserResponse(explanation);
    }
}
