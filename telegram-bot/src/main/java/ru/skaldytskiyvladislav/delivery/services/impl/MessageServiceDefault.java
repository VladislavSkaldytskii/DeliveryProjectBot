package ru.skaldytskiyvladislav.delivery.services.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.SerializationUtils;

import org.springframework.stereotype.Service;
import ru.skaldytskiyvladislav.delivery.models.entities.Message;
import ru.skaldytskiyvladislav.delivery.repositories.MessageRepository;
import ru.skaldytskiyvladislav.delivery.services.MessageService;

@Service
public class MessageServiceDefault implements MessageService {

    private MessageRepository messageRepository;

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private final Map<String, Message> cachedMessages = new HashMap<>();

    public MessageServiceDefault(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
        startCacheClearTask();
    }

    private void startCacheClearTask() {
        executorService.scheduleAtFixedRate(cachedMessages::clear, 20, 20, TimeUnit.MINUTES);
    }

    public void setRepository(MessageRepository repository) {
        this.messageRepository = repository;
    }

    @Override
    public Message findByName(String messageName) {
        if (messageName == null) {
            throw new IllegalArgumentException("MessageName should not be NULL");
        }

        Message message = cachedMessages.get(messageName);
        if (message == null) {
            message = messageRepository.findByName(messageName);
            cachedMessages.put(messageName, message);
        }

        return SerializationUtils.clone(message);
    }

}
