package ru.vladislavskaldytskiy.delivery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.vladislavskaldytskiy.delivery.models.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
