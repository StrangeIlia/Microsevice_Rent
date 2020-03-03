package bstu.BI.service.imp;

import bstu.BI.entity.enums.BookService_Operation;
import bstu.BI.service.BookService;
import bstu.BI.service.ExternalBookService;
import bstu.BI.util.Converter;
import bstu.BI.web.dto.BookService_Response;
import bstu.BI.web.dto.BookService_TransactionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImp implements BookService {
    @Autowired
    ExternalBookService externalBookService;

    @Override
    public Optional<BookService_TransactionInfo> startTransaction(Integer bookTypeId) {
        BookService_Response response = externalBookService.operation(bookTypeId, BookService_Operation.GET_BOOK);
        return Optional.ofNullable(Converter.convert(response));
    }

    @Override
    public void finishTransaction(BookService_TransactionInfo transactionInfo) {
        externalBookService.transaction(transactionInfo.getTransactionId());
    }

    @Override
    public void bookReturn(Integer bookTypeId) {
        externalBookService.operation(bookTypeId, BookService_Operation.PUSH_BOOK);
    }
}
