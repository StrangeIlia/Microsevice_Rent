package bstu.BI.service;

import bstu.BI.web.dto.DTO_UserService_Transaction;
import bstu.BI.web.dto.UserOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@FeignClient(name = "user-service", url = "${user_service}")
public interface ExternalUserService {
    @GetMapping("/info")
    UserOperation getInfo(@RequestParam String username);

    @PostMapping("/transactions")
    UserOperation transactions(@RequestBody @Valid DTO_UserService_Transaction data);
}
