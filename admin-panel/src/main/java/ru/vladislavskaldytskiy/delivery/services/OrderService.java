package ru.vladislavskaldytskiy.delivery.services;

import java.util.List;

import ru.vladislavskaldytskiy.delivery.models.entities.Order;

public interface OrderService {

    Order findById(Integer id);

    List<Order> findAll();

    Order save(Order order);

    Order update(Order order);

    void deleteById(Integer id);

}
