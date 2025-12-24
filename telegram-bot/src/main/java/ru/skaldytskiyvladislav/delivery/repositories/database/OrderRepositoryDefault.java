package ru.skaldytskiyvladislav.delivery.repositories.database;

import static ru.skaldytskiyvladislav.delivery.repositories.hibernate.HibernateTransactionFactory.inTransactionVoid;

import org.springframework.stereotype.Repository;
import ru.skaldytskiyvladislav.delivery.models.entities.Order;
import ru.skaldytskiyvladislav.delivery.repositories.OrderRepository;

@Repository
public class OrderRepositoryDefault implements OrderRepository {

    @Override
    public void save(Order order) {
        inTransactionVoid(session -> session.persist(order));
    }

}
