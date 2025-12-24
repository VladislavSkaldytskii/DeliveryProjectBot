package ru.skaldytskiyvladislav.delivery.handlers.commands;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ru.skaldytskiyvladislav.delivery.handlers.ActionHandler;
import ru.skaldytskiyvladislav.delivery.handlers.CommandHandler;
import ru.skaldytskiyvladislav.delivery.handlers.commands.registries.CommandHandlerRegistry;
import ru.skaldytskiyvladislav.delivery.models.domain.Button;
import ru.skaldytskiyvladislav.delivery.models.domain.ClientAction;
import ru.skaldytskiyvladislav.delivery.models.domain.ClientOrder;
import ru.skaldytskiyvladislav.delivery.models.domain.Command;
import ru.skaldytskiyvladislav.delivery.repositories.ClientActionRepository;
import ru.skaldytskiyvladislav.delivery.repositories.ClientCommandStateRepository;
import ru.skaldytskiyvladislav.delivery.repositories.ClientOrderStateRepository;

@Component

public class OrderEnterAddressCommandHandler implements CommandHandler, ActionHandler {

    private static final String ENTER_ADDRESS_ACTION = "order=enter-client-address";

    private static final Pattern ADDRESS_PATTERN = Pattern.compile("[а-яА-ЯёЁ]");

    private final CommandHandlerRegistry commandHandlerRegistry;
    private final ClientActionRepository clientActionRepository;
    private final ClientCommandStateRepository clientCommandStateRepository;
    private final ClientOrderStateRepository clientOrderStateRepository;
    @Autowired
    public OrderEnterAddressCommandHandler(@Lazy CommandHandlerRegistry commandHandlerRegistry, ClientActionRepository clientActionRepository, ClientCommandStateRepository clientCommandStateRepository, ClientOrderStateRepository clientOrderStateRepository) {
        this.commandHandlerRegistry = commandHandlerRegistry;
        this.clientActionRepository = clientActionRepository;
        this.clientCommandStateRepository = clientCommandStateRepository;
        this.clientOrderStateRepository = clientOrderStateRepository;
    }

    @Override
    public Command getCommand() {
        return Command.ENTER_ADDRESS;
    }

    @Override
    public void executeCommand(AbsSender absSender, Update update, Long chatId) throws TelegramApiException {
        clientActionRepository.updateByChatId(chatId, new ClientAction(getCommand(), ENTER_ADDRESS_ACTION));

        sendEnterAddressMessage(absSender, chatId);
        sendCurrentAddressMessage(absSender, chatId);
    }

    private void sendEnterAddressMessage(AbsSender absSender, Long chatId) throws TelegramApiException {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text("Введите адрес доставки или выберите его на карте ")
                .replyMarkup(buildReplyKeyboardMarkup(false))
                .build();
        absSender.execute(message);
    }

    private void sendCurrentAddressMessage(AbsSender absSender, Long chatId) throws TelegramApiException {
        ClientOrder clientOrder = clientOrderStateRepository.findByChatId(chatId);
        if (StringUtils.isBlank(clientOrder.getAddress())) {
            return;
        }

        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text("Текущий адрес: " + clientOrder.getAddress())
                .replyMarkup(buildReplyKeyboardMarkup(true))
                .build();
        absSender.execute(message);
    }

    private ReplyKeyboardMarkup buildReplyKeyboardMarkup(boolean skip) {
        ReplyKeyboardMarkup.ReplyKeyboardMarkupBuilder keyboardBuilder = ReplyKeyboardMarkup.builder();
        keyboardBuilder.resizeKeyboard(true);
        keyboardBuilder.selective(true);

        keyboardBuilder.keyboardRow(new KeyboardRow(Arrays.asList(
            KeyboardButton.builder()
                .text("Ввести самостоятельно ⬆\uFE0F")
                .build()

        )));

        keyboardBuilder.keyboardRow(new KeyboardRow(Arrays.asList(
            KeyboardButton.builder()
                .text("Выбрать на карте")
                .build()
        )));


        keyboardBuilder.keyboardRow(new KeyboardRow(Arrays.asList(
            KeyboardButton.builder()
                .text("Текущее местоположение📍")
                .requestLocation(true)
                .build()
        )));

        if (skip) {
            keyboardBuilder.keyboardRow(new KeyboardRow(Arrays.asList(
                KeyboardButton.builder().text(Button.ORDER_STEP_NEXT.getAlias()).build()
            )));
        }

        keyboardBuilder.keyboardRow(new KeyboardRow(Arrays.asList(
            KeyboardButton.builder().text(Button.ORDER_STEP_CANCEL.getAlias()).build(),
            KeyboardButton.builder().text(Button.ORDER_STEP_PREVIOUS.getAlias()).build()
        )));
        return keyboardBuilder.build();
    }

    @Override
    public boolean canHandleAction(Update update, String action) {
        return update.hasMessage() && update.getMessage().hasText() && ENTER_ADDRESS_ACTION.equals(action);
    }

    @Override
    public void handleAction(AbsSender absSender, Update update, String action) throws TelegramApiException {
        Long chatId = update.getMessage().getChatId();
        String text = update.getMessage().getText();


        if (update.getMessage().hasLocation()) {
            Location loc = update.getMessage().getLocation();

            String address = reverseGeocode(loc.getLatitude(), loc.getLongitude());

            saveClientOrderState(chatId, address);

            SendMessage msg = SendMessage.builder()
                .chatId(chatId)
                .text("Адрес выбран на карте:\n" + address)
                .build();
            absSender.execute(msg);

            executeNextCommand(absSender, update, chatId);
            return;
        }
        if ("Выбрать на карте".equals(update.getMessage().getText())) {
            SendMessage msg = SendMessage.builder()
                .chatId(chatId)
                .text("Вот ваша инструкция:\n1.Нажмите на скрепку слева\n2.Нажмите на геопозицию\n3.Выберите адрес")
                .build();
            absSender.execute(msg);
            return;
        }


        if (Button.ORDER_STEP_NEXT.getAlias().equals(text)) {
            executeNextCommand(absSender, update, chatId);
            return;
        }


        if (!ADDRESS_PATTERN.matcher(text).find()) {
            sendNotCorrectAddressMessage(absSender, chatId);
            return;
        }

        saveClientOrderState(chatId, text);
        executeNextCommand(absSender, update, chatId);
    }

    private void sendNotCorrectAddressMessage(AbsSender absSender, Long chatId) throws TelegramApiException {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text("You entered the incorrect address, try again.")
                .build();
        absSender.execute(message);
    }

    private void saveClientOrderState(Long chatId, String text) {
        ClientOrder clientOrder = clientOrderStateRepository.findByChatId(chatId);
        clientOrder.setAddress(text);
        clientOrderStateRepository.updateByChatId(chatId, clientOrder);
    }

    private void executeNextCommand(AbsSender absSender, Update update, Long chatId) throws TelegramApiException {
        clientCommandStateRepository.pushByChatId(chatId, getCommand());
        commandHandlerRegistry.find(Command.ORDER_CONFIRM).executeCommand(absSender, update, chatId);
    }

    private String reverseGeocode(double lat, double lon) {
        try {
            String url = String.format(
                "https://nominatim.openstreetmap.org/reverse?format=json&lat=%s&lon=%s&zoom=18&addressdetails=1",
                lat, lon
            );
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = reader.lines().collect(Collectors.joining());
            reader.close();

            JSONObject obj = new JSONObject(response);
            return obj.getString("display_name");

        } catch (Exception e) {
            return "lat=" + lat + ", lon=" + lon;
        }
    }

}
