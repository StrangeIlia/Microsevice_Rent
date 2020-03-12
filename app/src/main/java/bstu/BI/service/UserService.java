package bstu.BI.service;

import bstu.BI.web.dto.UserOperation;
import bstu.BI.web.dto.UserRequisites;

public interface UserService {
    UserOperation getInfo(String username);

    UserOperation transactions(UserRequisites requisites, Double cost);
}
