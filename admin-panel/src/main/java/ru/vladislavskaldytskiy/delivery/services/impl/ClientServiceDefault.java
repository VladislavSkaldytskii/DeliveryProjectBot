package ru.vladislavskaldytskiy.delivery.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.vladislavskaldytskiy.delivery.exceptions.ValidationException;
import ru.vladislavskaldytskiy.delivery.models.entities.Client;
import ru.vladislavskaldytskiy.delivery.repositories.ClientRepository;
import ru.vladislavskaldytskiy.delivery.services.ClientService;

@Service
public class ClientServiceDefault implements ClientService {

    private final ClientRepository repository;

    @Autowired
    public ClientServiceDefault(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Client findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id of Client should not be NULL");
        }

        return repository.findById(Long.valueOf(id)).orElse(null);
    }

    @Override
    public List<Client> findAll() {
        return repository.findAll();
    }

    @Override
    public Client update(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client should not be NULL");
        }
        if (client.getId() == null) {
            throw new ValidationException("Id of Client should not be NULL");
        }
        if (client.getChatId() == null) {
            throw new ValidationException("ChatId if Client should not be NULL");
        }

        return repository.save(client);
    }

    @Override
    public List<Client> findAllByActive(boolean active) {
        return repository.findAllByActive(active);
    }

}
