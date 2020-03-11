package bstu.BI.web.test;

import bstu.BI.entity.enums.Status;
import bstu.BI.web.dto.DTO_UserService_Transaction;
import bstu.BI.web.dto.UserOperation;
import bstu.BI.web.dto.UserRequisites;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("api/test/user-service")
public class UserService_Test {
    HashMap<String, Long> userRequisites = null;

    private void init() {
        if (Optional.ofNullable(userRequisites).isEmpty()) {
            userRequisites = new HashMap<>(3);
            userRequisites.put("Пользователь", 1L);
            userRequisites.put("Админ", 2L);
            userRequisites.put("Гость", 3L);
        }
    }

    @GetMapping("/info")
    public UserOperation getInfo(@RequestParam String username) {
        this.init();
        Long id = userRequisites.get(username);
        UserOperation userOperation = new UserOperation();
        if (Optional.ofNullable(id).isEmpty()) {
            userOperation.setStatus(Status.FAIL);
            userOperation.setExplanation("Нет пользователя с таким логином");
        } else {
            userOperation.setStatus(Status.SUCCESS);
            userOperation.setUserId(id);
        }
        return userOperation;
    }

    @PutMapping("/transactions")
    public UserOperation transactions(@RequestBody @Valid DTO_UserService_Transaction data) {
        UserRequisites requisites = data.getRequisites();
        Double cost = data.getCost();
        this.init();
        Long id = userRequisites.get(requisites.getUsername());
        UserOperation userOperation = new UserOperation();
        if (Optional.ofNullable(id).isEmpty()) {
            userOperation.setStatus(Status.FAIL);
            userOperation.setExplanation("Нет пользователя с таким логином");
        } else {
            userOperation.setStatus(Status.SUCCESS);
            userOperation.setUserId(id);
        }
        return userOperation;
    }
}
