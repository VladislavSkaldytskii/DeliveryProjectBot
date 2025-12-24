package ru.skaldytskiyvladislav.delivery.handlers.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ru.skaldytskiyvladislav.delivery.handlers.UpdateHandler;
import ru.skaldytskiyvladislav.delivery.models.domain.Button;
import ru.skaldytskiyvladislav.delivery.models.domain.Command;
import ru.skaldytskiyvladislav.delivery.repositories.ClientActionRepository;
import ru.skaldytskiyvladislav.delivery.repositories.ClientCommandStateRepository;
import ru.skaldytskiyvladislav.delivery.repositories.ClientOrderStateRepository;

@Component
@RequiredArgsConstructor
public class OrderStepCancelCommandHandler implements UpdateHandler {

    private final ClientActionRepository clientActionRepository;
    private final ClientCommandStateRepository clientCommandStateRepository;
    private final ClientOrderStateRepository clientOrderStateRepository;

    @Override
    public Command getCommand() {
        return Command.ORDER_STEP_CANCEL;
    }

    @Override
    public boolean canHandleUpdate(Update update) {
        return update.hasMessage() &&
                update.getMessage().hasText() &&
                update.getMessage().getText().startsWith(Button.ORDER_STEP_CANCEL.getAlias());
    }

    @Override
    public void handleUpdate(AbsSender absSender, Update update) throws TelegramApiException {
        Long chatId = update.getMessage().getChatId();

        clearClientOrderState(chatId);
        sendCanclelOrderMessage(absSender, chatId);
    }

    private void clearClientOrderState(Long chatId) {
        clientActionRepository.deleteByChatId(chatId);
        clientCommandStateRepository.deleteAllByChatId(chatId);
        clientOrderStateRepository.deleteByChatId(chatId);
    }

    private void sendCanclelOrderMessage(AbsSender absSender, Long chatId) throws TelegramApiException {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text("Отменить заказ.")
                .replyMarkup(Button.createGeneralMenuKeyboard())
                .build();
        absSender.execute(message);
    }

}
