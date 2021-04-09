package com.redhat.fuse.boosters.bookstore;

import com.redhat.fuse.boosters.bookstore.dto.ItemDto;
import org.apache.camel.Exchange;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;

@Component
public class ItemRoute extends ServiceRoute {
    @Override
    public void configure() throws Exception {

        from("direct:get_order_items")
                .process(e -> setParameters(e, "orderId", "orderId"))
                .to(jpa(Item.class, "findItemByOrderId"))
                .process(e -> UtilConverter.entitiesToDto(e, Item.class, ItemDto.class))
                .marshal(new JacksonDataFormat(ItemDto[].class))
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HTTP_OK));

        from("direct:new_item")
                .unmarshal(new JacksonDataFormat(ItemDto.class))
                .process(e -> UtilConverter.entityFromDto(e, Item.class, ItemDto.class))
                .process(e -> {
                    Item item = e.getIn().getBody(Item.class);
                    item.setOrderId(e.getIn().getHeader("orderId").toString());
                    e.getIn().setBody(item);
                })
                .to(jpaUpdate(Item.class))
                .process(e -> UtilConverter.entityToDto(e, Item.class, ItemDto.class))
                .marshal(new JacksonDataFormat(ItemDto.class))
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HTTP_CREATED));
    }
}
