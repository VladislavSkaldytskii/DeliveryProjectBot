package ru.vladislavskaldytskiy.delivery.services;

import java.util.List;

import ru.vladislavskaldytskiy.delivery.models.entities.Message;

public interface MessageService {

    Message findById(Integer id);

    Message update(Message message);

    List<Message> findAll();

}
