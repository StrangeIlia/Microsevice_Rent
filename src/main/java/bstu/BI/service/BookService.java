package bstu.BI.service;

import bstu.BI.web.dto.BookServiceResponse;
import bstu.BI.web.dto.ServiceResponse;

public interface BookService {
    BookServiceResponse rentBook(Long bookTypeId);

    ServiceResponse returnBook(Long bookTypeId);
}
