package ru.skaldytskiyvladislav.delivery.repositories;

import ru.skaldytskiyvladislav.delivery.models.domain.Command;

public interface ClientCommandStateRepository {

    void pushByChatId(Long chatId, Command command);

    Command popByChatId(Long chatId);

    void deleteAllByChatId(Long chatId);

}
