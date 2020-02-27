package bstu.BI.service;

import bstu.BI.web.dto.UserOperation;
import bstu.BI.web.dto.UserRequisites;

public interface UserService {
    UserOperation getUserId(UserRequisites requisites);

    UserOperation withdrawingMoneyFromAnAccount(UserRequisites requisites, Double cost);

    UserOperation returnOfMoneyToAccount(Integer userId, Double cost);
}
