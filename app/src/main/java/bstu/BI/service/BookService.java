package bstu.BI.service;

import bstu.BI.entity.domain.RentalTicket;

import java.util.Optional;

public interface BookService {
    Optional<RentalTicket> buyOne(Long bookTypeId);

    boolean returnOne(Long bookTypeId);
}
