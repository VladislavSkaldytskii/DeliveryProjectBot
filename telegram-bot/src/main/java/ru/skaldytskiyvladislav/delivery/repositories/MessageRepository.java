package ru.skaldytskiyvladislav.delivery.repositories;

import ru.skaldytskiyvladislav.delivery.models.entities.Message;

public interface MessageRepository {

    Message findByName(String messageName);

}
