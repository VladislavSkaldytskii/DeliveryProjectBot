package ru.skaldytskiyvladislav.delivery.repositories.database;

import static ru.skaldytskiyvladislav.delivery.repositories.hibernate.HibernateTransactionFactory.inTransaction;

import org.springframework.stereotype.Repository;
import ru.skaldytskiyvladislav.delivery.models.entities.Message;
import ru.skaldytskiyvladislav.delivery.repositories.MessageRepository;

@Repository
public class MessageRepositoryDefault implements MessageRepository {

    @Override
    public Message findByName(String messageName) {
        String query = "from Message where name = :name";

        return inTransaction(session ->
                session.createQuery(query, Message.class)
                        .setParameter("name", messageName)
                        .setMaxResults(1)
                        .uniqueResult()
        );
    }

}
