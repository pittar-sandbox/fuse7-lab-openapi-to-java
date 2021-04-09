package com.redhat.fuse.boosters.bookstore;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public abstract class ServiceRoute extends RouteBuilder {

    protected String jpa(Class clazz, String namedQuery) {
        return "jpa://" + clazz.getCanonicalName() + "?namedQuery=" + namedQuery;
    }

    protected String jpaUpdate(Class clazz) {
        return "jpa://" + clazz.getCanonicalName() + "?useExecuteUpdate=true";
    }

    protected void setParameters(Exchange exchange, String parameter, String header) {
        Map<String, Object> map = new HashMap<>();
        map.put(parameter, exchange.getIn().getHeader(header));
        exchange.getIn().setHeader("CamelJpaParameters", map);
    }
}
