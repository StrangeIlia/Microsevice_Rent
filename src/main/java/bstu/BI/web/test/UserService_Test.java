package bstu.BI.web.test;

import bstu.BI.entity.enums.Status;
import bstu.BI.web.v1.dto.UserOperation;
import bstu.BI.web.v1.dto.UserRequisites;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("api/test/user-service")
public class UserService_Test {
    HashMap<String, Integer> userRequisites = null;

    private void init() {
        if (Optional.ofNullable(userRequisites).isEmpty()) {
            userRequisites = new HashMap<>(3);
            userRequisites.put("Пользователь", 1);
            userRequisites.put("Админ", 2);
            userRequisites.put("Гость", 3);
        }
    }

    @GetMapping("/info")
    public UserOperation getInfo(@RequestParam UserRequisites requisites) {
        Integer id = userRequisites.get(requisites.getUsername());
        UserOperation userOperation = new UserOperation();
        if (Optional.ofNullable(id).isEmpty()) {
            userOperation.setStatus(Status.FAIL);
            userOperation.setExplanation("Нет пользоателя с таким логином");
        } else {
            userOperation.setStatus(Status.SUCCESS);
            userOperation.setUserId(id);
        }
        return userOperation;
    }

    @PostMapping("/transactions")
    public UserOperation transactions(@RequestParam UserRequisites requisites,
                                      @RequestParam Double cost) {
        Integer id = userRequisites.get(requisites.getUsername());
        UserOperation userOperation = new UserOperation();
        if (Optional.ofNullable(id).isEmpty()) {
            userOperation.setStatus(Status.FAIL);
            userOperation.setExplanation("Нет пользоателя с таким логином");
        } else {
            userOperation.setStatus(Status.SUCCESS);
            userOperation.setUserId(id);
        }
        return userOperation;
    }
}
