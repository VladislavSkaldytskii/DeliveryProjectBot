package ru.skaldytskiyvladislav.delivery.repositories;

import ru.skaldytskiyvladislav.delivery.models.entities.Order;

public interface OrderRepository {

    void save(Order order);

}
