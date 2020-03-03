package bstu.BI.service;

import bstu.BI.web.dto.BookService_TransactionInfo;

import java.util.Optional;

public interface BookService {
    Optional<BookService_TransactionInfo> startTransaction(Integer bookTypeId);

    void finishTransaction(BookService_TransactionInfo transactionInfo);

    void bookReturn(Integer bookTypeId);
}
