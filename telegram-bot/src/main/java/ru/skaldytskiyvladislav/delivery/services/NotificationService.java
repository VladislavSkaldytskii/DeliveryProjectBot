package ru.skaldytskiyvladislav.delivery.services;

import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ru.skaldytskiyvladislav.delivery.models.entities.Order;

public interface NotificationService {

    void notifyAdminChatAboutNewOrder(AbsSender absSender, Order order) throws TelegramApiException;

}
