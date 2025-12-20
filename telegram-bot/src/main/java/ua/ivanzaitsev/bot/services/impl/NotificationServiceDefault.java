package ua.ivanzaitsev.bot.services.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ua.ivanzaitsev.bot.core.ConfigReader;
import ua.ivanzaitsev.bot.models.entities.Client;
import ua.ivanzaitsev.bot.models.entities.Courier;
import ua.ivanzaitsev.bot.models.entities.Order;
import ua.ivanzaitsev.bot.models.entities.OrderItem;
import ua.ivanzaitsev.bot.repositories.CourierRepository;
import ua.ivanzaitsev.bot.services.NotificationService;

@Service
public class NotificationServiceDefault implements NotificationService {

    private final String adminPanelBaseUrl;
    private final CourierRepository courierRepository;

    public NotificationServiceDefault(ConfigReader configReader , CourierRepository courierRepository) {
        this.courierRepository = courierRepository;
        this.adminPanelBaseUrl = configReader.get("admin-panel.base-url");

    }

    @Override
    public void notifyAdminChatAboutNewOrder(AbsSender absSender, Order order)
        throws TelegramApiException {

        List<Courier> couriers = courierRepository.findAllByActive(true);

        if (couriers.isEmpty()) {
            return;
        }

        for (Courier courier : couriers) {
            if (courier.getClient().getChatId() == null) {
                continue;
            }

            Long chatId = courier.getClient().getChatId();
            if (chatId == null) {
                continue;
            }

            sendOrderAndClientInformationMessage(absSender, order, courier.getClient().getChatId());
//            sendOrderItemsInformationMessage(absSender, order, courier.getClient().getChatId());
        }
    }

    private void sendOrderAndClientInformationMessage(
        AbsSender absSender,
        Order order,
        Long chatId
    ) throws TelegramApiException {

        SendMessage message = SendMessage.builder()
            .chatId(chatId.toString())
            .text(createOrderAndClientInformation(order))
            .parseMode("HTML")
            .build();

        absSender.execute(message);
    }

//    private void sendOrderItemsInformationMessage(
//        AbsSender absSender,
//        Order order,
//        Long chatId
//    ) throws TelegramApiException {
//
//        SendMessage message = SendMessage.builder()
//            .chatId(chatId.toString())
//            .text(createOrderItemsInformation(order))
//            .parseMode("HTML")
//            .build();
//
//        absSender.execute(message);
//    }

    private String createOrderAndClientInformation(Order order) {
        return "#Заказ_" + order.getId() + "\n" +
            "<b>Информация о клиенте</b>:\n" +
            buildClientInformation(order.getClient()) + "\n\n" +
            "<b>Информация о заказе</b>:\n" +
            "Заказанные товары: " + "\n" +
            buildOrderItemsInformation(order.getItems());
    }

//    private String buildOrderInformation(Order order) {
//        return "-Сумма: " + order.getAmount() + " ₽";
//    }

    private String buildClientInformation(Client client) {
        return "-Имя: " + client.getName() + "\n" +
            "-Номер телефона: " + client.getPhoneNumber() + "\n" +
            "-Город: " + client.getCity() + "\n" +
            "-Адрес: " + client.getAddress() + "\n" +
            "<a href=\"tg://user?id=" + client.getChatId() + "\">Открыть профиль</a>";
    }

//    private String createOrderItemsInformation(Order order) {
//        return "#Заказ_" + order.getId() + "\n" +
//            "<b>Заказанные товары</b>:\n" +
//            buildOrderItemsInformation(order.getItems());
//    }

    private String buildOrderItemsInformation(List<OrderItem> orderItems) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < orderItems.size(); i++) {
            OrderItem item = orderItems.get(i);

            result.append(i + 1)
                .append(") ")
                .append(item.getProductName())
                .append(" — ")
                .append(item.getQuantity())
                .append(" шт. = ")
                .append(item.getProductPrice() * item.getQuantity())
                .append(" ₽\n");
        }

        return result.toString();
    }
}
