package com.redhat.fuse.boosters.bookstore;

import com.redhat.fuse.boosters.bookstore.dto.BookDto;
import org.apache.camel.Exchange;
import org.springframework.beans.BeanUtils;

public class EntityConverter {

    public static void bookFromDto(Exchange exchange) {
        BookDto dto = exchange.getIn().getBody(BookDto.class);
        Book book = new Book();
        BeanUtils.copyProperties(dto, book);
        exchange.getIn().setBody(book);
    }

    public static void bookToDto(Exchange exchange) {
        Book book = exchange.getIn().getBody(Book.class);
        BookDto dto = new BookDto();
        BeanUtils.copyProperties(book, dto);
        exchange.getIn().setBody(dto);
    }
}
