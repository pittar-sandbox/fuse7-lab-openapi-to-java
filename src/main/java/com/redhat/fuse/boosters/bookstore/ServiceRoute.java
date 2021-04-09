package com.redhat.fuse.boosters.bookstore;

import com.redhat.fuse.boosters.bookstore.dto.BookDto;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ServiceRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("direct:get_books")
                .to(jpa(Book.class, "findAll"))
                .marshal(new JacksonDataFormat(BookDto[].class));

        from("direct:new_book")
                .unmarshal(new JacksonDataFormat(BookDto.class))
                .process(EntityConverter::bookFromDto)
                .to(jpaUpdate(Book.class))
                .process(EntityConverter::bookToDto)
                .marshal(new JacksonDataFormat(BookDto.class));

        from("direct:get_book_by_isbn")
                .process(e -> setParameters(e, "isbn", "isbn"))
                .to(jpa(Book.class, "findByIsbn"))
                .marshal(new JacksonDataFormat(BookDto.class));
    }

    private String jpa(Class clazz, String namedQuery) {
        return "jpa://" + clazz.getCanonicalName() + "?namedQuery=" + namedQuery;
    }

    private String jpaUpdate(Class clazz) {
        return "jpa://" + clazz.getCanonicalName() + "?useExecuteUpdate=true";
    }

    private void setParameters(Exchange exchange, String parameter, String header) {
        Map<String, Object> map = new HashMap<>();
        map.put(parameter, exchange.getIn().getHeader(header));
        exchange.getIn().setHeader("CamelJpaParameters", map);
    }
}
