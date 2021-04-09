package com.redhat.fuse.boosters.bookstore;

import com.redhat.fuse.boosters.bookstore.dto.BookDto;
import org.apache.camel.Exchange;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

import static java.net.HttpURLConnection.*;

@Component
public class BookRoute extends ServiceRoute {
    @Override
    public void configure() throws Exception {

        from("direct:get_books")
                .to(jpa(Book.class, "findAllBooks"))
                .process(e -> UtilConverter.entitiesToDto(e, Book.class, BookDto.class))
                .marshal(new JacksonDataFormat(BookDto[].class))
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HTTP_OK));

        from("direct:new_book")
                .unmarshal(new JacksonDataFormat(BookDto.class))
                .process(e -> UtilConverter.entityFromDto(e, Book.class, BookDto.class))
                .to(jpaUpdate(Book.class))
                .process(e -> UtilConverter.entityToDto(e, Book.class, BookDto.class))
                .marshal(new JacksonDataFormat(BookDto.class))
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HTTP_CREATED));

        from("direct:get_book_by_isbn")
                .process(e -> setParameters(e, "isbn", "isbn"))
                .to(jpa(Book.class, "findBookByIsbn"))
                .choice()
                    .when(body().isInstanceOf(Book.class))
                        .process(e -> UtilConverter.entityToDto(e, Book.class, BookDto.class))
                        .marshal(new JacksonDataFormat(BookDto.class))
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HTTP_OK))
                    .otherwise()
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HTTP_NOT_FOUND));

    }
}
