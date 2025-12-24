package ru.skaldytskiyvladislav.delivery.repositories.database;

import static ru.skaldytskiyvladislav.delivery.repositories.hibernate.HibernateTransactionFactory.inTransaction;
import static ru.skaldytskiyvladislav.delivery.repositories.hibernate.HibernateTransactionFactory.inTransactionVoid;

import org.springframework.stereotype.Repository;
import ru.skaldytskiyvladislav.delivery.models.entities.Client;
import ru.skaldytskiyvladislav.delivery.repositories.ClientRepository;

@Repository
public class ClientRepositoryDefault implements ClientRepository {

    @Override
    public Client findByChatId(Long chatId) {
        String query = "from Client where chatId = :chatId";

        return inTransaction(session ->
                session.createQuery(query, Client.class)
                        .setParameter("chatId", chatId)
                        .setMaxResults(1)
                        .uniqueResult()
        );
    }

    @Override
    public void save(Client client) {
        inTransactionVoid(session -> session.persist(client));
    }

    @Override
    public void update(Client client) {
        inTransactionVoid(session -> session.merge(client));
    }

}
