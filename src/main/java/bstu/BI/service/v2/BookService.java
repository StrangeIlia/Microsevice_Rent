package bstu.BI.service.v2;

import bstu.BI.web.v2.dto.BookService_TransactionInfo;

import java.util.Optional;

public interface BookService {
    Optional<BookService_TransactionInfo> startTransaction(Integer bookTypeId);

    void finishTransaction(BookService_TransactionInfo transactionInfo);

    void bookReturn(Integer bookTypeId);
}
