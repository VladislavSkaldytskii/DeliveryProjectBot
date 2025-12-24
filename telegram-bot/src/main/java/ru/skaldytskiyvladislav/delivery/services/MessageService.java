package ru.skaldytskiyvladislav.delivery.services;


import ru.skaldytskiyvladislav.delivery.models.entities.Message;

public interface MessageService {

    Message findByName(String messageName);

}
