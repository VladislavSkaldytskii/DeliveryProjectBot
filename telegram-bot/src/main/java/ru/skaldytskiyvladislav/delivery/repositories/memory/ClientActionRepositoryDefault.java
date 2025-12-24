package ru.skaldytskiyvladislav.delivery.repositories.memory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.SerializationUtils;

import org.springframework.stereotype.Repository;
import ru.skaldytskiyvladislav.delivery.models.domain.ClientAction;
import ru.skaldytskiyvladislav.delivery.repositories.ClientActionRepository;

@Repository
public class ClientActionRepositoryDefault implements ClientActionRepository {

    private final Map<Long, ClientAction> clientsAction = new ConcurrentHashMap<>();

    @Override
    public ClientAction findByChatId(Long chatId) {
        ClientAction clientAction = clientsAction.get(chatId);
        return SerializationUtils.clone(clientAction);
    }

    @Override
    public void updateByChatId(Long chatId, ClientAction clientAction) {
        clientsAction.put(chatId, SerializationUtils.clone(clientAction));
    }

    @Override
    public void deleteByChatId(Long chatId) {
        clientsAction.remove(chatId);
    }

}
