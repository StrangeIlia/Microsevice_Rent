package bstu.BI.service;

import bstu.BI.web.dto.UserRequisites;
import bstu.BI.web.dto.UserServiceResponse;

public interface UserService {
    UserServiceResponse getInfo(String username);

    UserServiceResponse transactions(UserRequisites requisites, Double cost);
}
