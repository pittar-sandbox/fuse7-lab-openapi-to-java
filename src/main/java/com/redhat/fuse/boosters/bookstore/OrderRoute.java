package com.redhat.fuse.boosters.bookstore;

import com.redhat.fuse.boosters.bookstore.dto.BookDto;
import com.redhat.fuse.boosters.bookstore.dto.OrderDto;
import org.apache.camel.Exchange;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

import static java.net.HttpURLConnection.*;

@Component
public class OrderRoute extends ServiceRoute {
    @Override
    public void configure() throws Exception {

        from("direct:get_orders")
                .to(jpa(Order.class, "findAllOrders"))
                .process(e -> UtilConverter.entitiesToDto(e, Order.class, OrderDto.class))
                .marshal(new JacksonDataFormat(BookDto[].class))
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HTTP_OK));

        from("direct:new_order")
                .unmarshal(new JacksonDataFormat(OrderDto.class))
                .process(e -> UtilConverter.entityFromDto(e, Order.class, OrderDto.class))
                .to(jpaUpdate(Order.class))
                .process(e -> UtilConverter.entityToDto(e, Order.class, OrderDto.class))
                .marshal(new JacksonDataFormat(OrderDto.class))
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HTTP_CREATED));

        from("direct:update_order")
                .unmarshal(new JacksonDataFormat(OrderDto.class))
                .process(e -> UtilConverter.entityFromDto(e, Order.class, OrderDto.class))
                .to(jpaUpdate(Order.class))
                .process(e -> UtilConverter.entityToDto(e, Order.class, OrderDto.class))
                .marshal(new JacksonDataFormat(OrderDto.class))
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HTTP_CREATED));

        from("direct:get_order_by_id")
                .process(e -> setParameters(e, "orderId", "orderId"))
                .to(jpa(Order.class, "findOrderById"))
                .choice()
                    .when(body().isInstanceOf(Book.class))
                        .process(e -> UtilConverter.entityToDto(e, Order.class, OrderDto.class))
                        .marshal(new JacksonDataFormat(OrderDto.class))
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HTTP_OK))
                    .otherwise()
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HTTP_NOT_FOUND));
    }
}
