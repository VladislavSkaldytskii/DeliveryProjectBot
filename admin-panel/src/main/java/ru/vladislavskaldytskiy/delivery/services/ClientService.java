package ru.vladislavskaldytskiy.delivery.services;

import java.util.List;

import ru.vladislavskaldytskiy.delivery.models.entities.Client;

public interface ClientService {

    Client findById(Integer id);

    List<Client> findAll();

    Client update(Client client);

    List<Client> findAllByActive(boolean active);

}
