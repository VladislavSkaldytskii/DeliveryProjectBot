package ru.skaldytskiyvladislav.delivery.repositories;

import ru.skaldytskiyvladislav.delivery.models.entities.Client;

public interface ClientRepository {

    Client findByChatId(Long chatId);

    void save(Client client);

    void update(Client client);

}
