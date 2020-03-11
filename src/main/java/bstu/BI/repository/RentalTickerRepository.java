package bstu.BI.repository;

import bstu.BI.entity.domain.RentalTicket;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface RentalTickerRepository extends CrudRepository<RentalTicket, Integer> {
    Collection<RentalTicket> findByUserId(Long userId);
}
