package com.redhat.fuse.boosters.bookstore;

import org.apache.camel.Exchange;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class UtilConverter {

    public static <T, S> void entityFromDto(Exchange exchange, Class<T>  classEntity, Class<S>  classDto) throws Exception {
        Object dto = exchange.getIn().getBody(classDto);
        T entity = (T) classEntity.getDeclaredConstructor().newInstance();
        BeanUtils.copyProperties(dto, entity);
        exchange.getIn().setBody(entity);
    }

    public static <T, S> void entityToDto(Exchange exchange, Class<T>  classEntity, Class<S>  classDto) throws Exception {
        T entity = exchange.getIn().getBody(classEntity);
        S dto = entityToDto(entity, classDto);
        exchange.getIn().setBody(dto);
    }

    public static <T, S> void entitiesToDto(Exchange exchange, Class<T>  classEntity, Class<S>  classDto) throws Exception {
        List<T> entities = exchange.getIn().getBody(List.class);
        List<S> dtos = new ArrayList<>(entities.size());
        for(T entity : entities){
            S dto = entityToDto(entity, classDto);
            dtos.add(dto);
        }
        exchange.getIn().setBody(dtos);
    }

    private static <T, S>  S entityToDto(T entity, Class<S> dtoClass) throws Exception {
        S dto = dtoClass.getDeclaredConstructor().newInstance();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
