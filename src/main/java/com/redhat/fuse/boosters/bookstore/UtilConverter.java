package com.redhat.fuse.boosters.bookstore;

import com.redhat.fuse.boosters.bookstore.dto.BookDto;
import org.apache.camel.Exchange;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class UtilConverter {

    public static void bookFromDto(Exchange exchange) {
        BookDto dto = exchange.getIn().getBody(BookDto.class);
        Book book = new Book();
        BeanUtils.copyProperties(dto, book);
        exchange.getIn().setBody(book);
    }

    public static void bookToDto(Exchange exchange) {
        Book book = exchange.getIn().getBody(Book.class);
        BookDto dto = bookToDto(book);
        exchange.getIn().setBody(dto);
    }

    public static void booksToDto(Exchange exchange) {
        List<Book> books = exchange.getIn().getBody(List.class);
        List<BookDto> dtos = books.stream().map(UtilConverter::bookToDto).collect(Collectors.toList());
        exchange.getIn().setBody(dtos);
    }

    private static BookDto bookToDto(Book book){
        BookDto dto = new BookDto();
        BeanUtils.copyProperties(book, dto);
        return dto;
    }
}
