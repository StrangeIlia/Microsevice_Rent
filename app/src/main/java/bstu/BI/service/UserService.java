package bstu.BI.service;

import bstu.BI.web.dto.user_service.DTO_UserService_UserRequisites;

import java.util.Optional;

public interface UserService {
    Optional<Long> getInfo(String username);

    Optional<Long> transactions(DTO_UserService_UserRequisites requisites, Double cost);
}
