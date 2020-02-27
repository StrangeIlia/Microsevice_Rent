package bstu.BI.service.imp.v1;

import bstu.BI.entity.domain.RentalTicket;
import bstu.BI.service.BookService;
import bstu.BI.web.dto.BookRefundInfo;
import bstu.BI.web.dto.BookRentInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Service
public class BookServiceImp implements BookService {
    @Override
    public Optional<RentalTicket> startTransaction(Integer bookTypeId) {
        RestTemplate restTemplate = new RestTemplate();
        Integer typeId = bookTypeId;
        ResponseEntity<BookRentInfo> responseEntity =
                restTemplate.getForEntity("api/books", BookRentInfo.class, new Object() {
                    Integer bookTypeId = typeId;
                });
        if (!responseEntity.hasBody())
            return Optional.empty();
        BookRentInfo bookRentInfo = responseEntity.getBody();
        if (bookRentInfo.isFail())
            return Optional.empty();
        RentalTicket result = new RentalTicket();
        result.setBookId(bookRentInfo.getId());
        result.setRentPrice(bookRentInfo.getRentPrice());
        result.setPurchasePrice(bookRentInfo.getPurchasePrice());
        return Optional.of(result);
    }

    @Override
    public void finishTransaction(RentalTicket rentalTicket) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put("api/books", new Object() {
            Integer bookId = rentalTicket.getBookId();
        });
    }

    @Override
    public Collection<RentalTicket> getReturnedBooks(Collection<RentalTicket> rentalTickets) {
        RestTemplate restTemplate = new RestTemplate();
        Collection<Object> shippingBooks = new ArrayList<>(rentalTickets.size());
        for (RentalTicket rentalTicket : rentalTickets)
            shippingBooks.add(new Object() {
                Integer id = rentalTicket.getBookId();
            });
        ResponseEntity<ArrayList> responseEntity = restTemplate.getForEntity(
                "api/books/returned", ArrayList.class, shippingBooks);
        if (!responseEntity.hasBody())
            return new ArrayList<>();
        ArrayList list = responseEntity.getBody();
        HashSet<RentalTicket> result = new HashSet<>(list.size());
        for (Object obj : list) {
            BookRefundInfo bookRefundInfo = (BookRefundInfo) obj;
            for (RentalTicket ticket : rentalTickets) {
                if (ticket.getBookId().equals(bookRefundInfo.getId())) {
                    long max = ticket.getRentalStart().getTime() + ticket.getRentalPeriod().getTime();
                    if (max >= bookRefundInfo.getReturnTime().getTime()) {
                        result.add(ticket);
                        break;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void returnBooks(Collection<RentalTicket> rentalTickets) {
        RestTemplate restTemplate = new RestTemplate();
        Collection<Object> shippingBooks = new ArrayList<>(rentalTickets.size());
        for (RentalTicket rentalTicket : rentalTickets)
            shippingBooks.add(new Object() {
                Integer id = rentalTicket.getBookId();
            });
        restTemplate.put("api/books/clear", shippingBooks);
    }
}
