package bstu.BI.service.v2;

import bstu.BI.web.v1.dto.UserOperation;
import bstu.BI.web.v1.dto.UserRequisites;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "localhost:50001/api/test/user-service")
public interface UserService {
    @GetMapping("/info")
    UserOperation getInfo(@RequestParam UserRequisites requisites);

    @PostMapping("/transactions")
    UserOperation transactions(@RequestParam UserRequisites requisites,
                               @RequestParam Double cost);
}
