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
import ru.skaldytskiyvladislav.delivery.models.entities.Client;
import ru.skaldytskiyvladislav.delivery.repositories.ClientRepository;
import ru.skaldytskiyvladislav.delivery.services.MessageService;

@Component
@RequiredArgsConstructor
public class StartCommandHandler implements UpdateHandler {

    private final ClientRepository clientRepository;
    private final MessageService messageService;

    @Override
    public Command getCommand() {
        return Command.START;
    }

    @Override
    public boolean canHandleUpdate(Update update) {
        return update.hasMessage() &&
                update.getMessage().hasText() &&
                update.getMessage().getText().startsWith(Button.START.getAlias());
    }

    @Override
    public void handleUpdate(AbsSender absSender, Update update) throws TelegramApiException {
        Long chatId = update.getMessage().getChatId();

        saveClient(chatId);
        sendStartMessage(absSender, chatId);
    }

    private void saveClient(Long chatId) {
        Client client = clientRepository.findByChatId(chatId);

        if (client == null) {
            createClient(chatId);
        } else if (!client.isActive()) {
            activateClient(client);
        }
    }

    private void createClient(Long chatId) {
        Client client = new Client();
        client.setChatId(chatId);
        client.setActive(true);
        clientRepository.save(client);
    }

    private void activateClient(Client client) {
        client.setActive(true);
        clientRepository.update(client);
    }

    private void sendStartMessage(AbsSender absSender, Long chatId) throws TelegramApiException {
        String text = messageService.findByName("START_MESSAGE").buildText();

        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(Button.createGeneralMenuKeyboard())
                .build();
        absSender.execute(message);
    }

}
