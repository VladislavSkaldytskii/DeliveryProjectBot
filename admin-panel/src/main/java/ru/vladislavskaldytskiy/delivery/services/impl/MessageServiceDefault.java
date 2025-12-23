package ru.vladislavskaldytskiy.delivery.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.vladislavskaldytskiy.delivery.models.entities.Message;
import ru.vladislavskaldytskiy.delivery.repositories.MessageRepository;
import ru.vladislavskaldytskiy.delivery.services.MessageService;

@Service
public class MessageServiceDefault implements MessageService {

    private final MessageRepository repository;

    @Autowired
    public MessageServiceDefault(MessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public Message findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id of Message should not be NULL");
        }

        return repository.findById(id).orElse(null);
    }

    @Override
    public Message update(Message message) {
        if (message == null) {
            throw new IllegalArgumentException("Message should not be NULL");
        }

        return repository.save(message);
    }

    @Override
    public List<Message> findAll() {
        return repository.findAll();
    }

}
