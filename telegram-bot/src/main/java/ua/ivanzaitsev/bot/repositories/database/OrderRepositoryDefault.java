package ua.ivanzaitsev.bot.repositories.database;

import static ua.ivanzaitsev.bot.repositories.hibernate.HibernateTransactionFactory.inTransactionVoid;

import org.springframework.stereotype.Repository;
import ua.ivanzaitsev.bot.models.entities.Order;
import ua.ivanzaitsev.bot.repositories.OrderRepository;

@Repository
public class OrderRepositoryDefault implements OrderRepository {

    @Override
    public void save(Order order) {
        inTransactionVoid(session -> session.persist(order));
    }

}
