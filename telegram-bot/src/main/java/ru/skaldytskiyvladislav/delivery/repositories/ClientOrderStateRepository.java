package ru.skaldytskiyvladislav.delivery.repositories;

import ru.skaldytskiyvladislav.delivery.models.domain.ClientOrder;

public interface ClientOrderStateRepository {

    ClientOrder findByChatId(Long chatId);

    void updateByChatId(Long chatId, ClientOrder clientOrder);

    void deleteByChatId(Long chatId);

}
