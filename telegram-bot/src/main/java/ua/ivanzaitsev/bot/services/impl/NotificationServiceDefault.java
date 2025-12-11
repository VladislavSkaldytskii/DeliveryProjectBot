package ua.ivanzaitsev.bot.services.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ua.ivanzaitsev.bot.core.ConfigReader;
import ua.ivanzaitsev.bot.models.entities.Client;
import ua.ivanzaitsev.bot.models.entities.Order;
import ua.ivanzaitsev.bot.models.entities.OrderItem;
import ua.ivanzaitsev.bot.services.NotificationService;

@Service
public class NotificationServiceDefault implements NotificationService {

    private final String adminPanelBaseUrl;
    private final List<Long> telegramAdminChatIds;

    public NotificationServiceDefault(ConfigReader configReader) {
        this.adminPanelBaseUrl = configReader.get("admin-panel.base-url");


        String chatIdsRaw = configReader.get("telegram.admin.chat-id");

        this.telegramAdminChatIds = Arrays.stream(chatIdsRaw.split(","))
            .map(String::trim)
            .map(Long::parseLong)
            .toList();
    }

    @Override
    public void notifyAdminChatAboutNewOrder(AbsSender absSender, Order order) throws TelegramApiException {
        for (Long adminChatId : telegramAdminChatIds) {
            sendOrderAndClientInformationMessage(absSender, order, adminChatId);
            sendOrderItemsInformationMessage(absSender, order, adminChatId);
        }
    }

    private void sendOrderAndClientInformationMessage(AbsSender absSender, Order order, Long adminChatId) throws TelegramApiException {
        SendMessage message = SendMessage.builder()
            .chatId(adminChatId)
            .text(createOrderAndClientInformation(order))
            .parseMode("HTML")
            .build();
        absSender.execute(message);
    }

    private void sendOrderItemsInformationMessage(AbsSender absSender, Order order, Long adminChatId) throws TelegramApiException {
        SendMessage message = SendMessage.builder()
            .chatId(adminChatId)
            .text(createOrderItemsInformation(order))
            .parseMode("HTML")
            .build();
        absSender.execute(message);
    }

    private String createOrderAndClientInformation(Order order) {
        return "#Заказ_" + order.getId() + "\n" +
            //"<b>Order url</b>:\n" + buildOrderUrl(order.getId()) + "\n\n" +
            "<b>Информация о заказе </b>:\n" + buildOrderInformation(order) + "\n\n" +
            "<b>Информация о клиенте</b>:\n" + buildClientInformation(order.getClient());
    }

    private String buildOrderUrl(Integer orderId) {
        return adminPanelBaseUrl + "/orders/edit/" + orderId;
    }

    private String buildOrderInformation(Order order) {
        return "-Сумма: " + order.getAmount() + " ₽";
    }

    private String buildClientInformation(Client client) {
        return "-Имя: " + client.getName() + "\n" +
            "-Номер телефона: " + client.getPhoneNumber() + "\n" +
            "-Город: " + client.getCity() + "\n" +
            "-Адрес: " + client.getAddress() + "\n" +
            "<a href=\"tg://user?id=" + client.getChatId() + "\">Открыть профиль</a>";
    }

    private String createOrderItemsInformation(Order order) {
        return "#Заказ_" + order.getId() + "\n" +
            "<b>Заказанные товары</b>:\n" + buildOrderItemsInformation(order.getItems());
    }

    private String buildOrderItemsInformation(List<OrderItem> orderItems) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < orderItems.size(); i++) {
            OrderItem orderItem = orderItems.get(i);

            result.append(i + 1).append(") ").append(orderItem.getProductName()).append(" — ")
                .append(orderItem.getQuantity()).append(" шт. = ")
                .append(orderItem.getProductPrice() * orderItem.getQuantity()).append(" ₽\n");
        }

        return result.toString();
    }

}
