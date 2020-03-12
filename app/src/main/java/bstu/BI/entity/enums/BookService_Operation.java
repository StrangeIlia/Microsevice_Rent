package bstu.BI.entity.enums;

import lombok.Getter;

@Getter
public enum BookService_Operation {
    GET_BOOK(-1),
    PUSH_BOOK(1);

    private int value;

    BookService_Operation(int value) {
        this.value = value;
    }
}
