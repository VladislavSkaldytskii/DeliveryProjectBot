package ru.skaldytskiyvladislav.delivery.handlers.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ru.skaldytskiyvladislav.delivery.handlers.CommandHandler;
import ru.skaldytskiyvladislav.delivery.handlers.UpdateHandler;
import ru.skaldytskiyvladislav.delivery.handlers.commands.registries.CommandHandlerRegistry;
import ru.skaldytskiyvladislav.delivery.models.domain.Button;
import ru.skaldytskiyvladislav.delivery.models.domain.Command;
import ru.skaldytskiyvladislav.delivery.repositories.ClientCommandStateRepository;

@Component

public class OrderStepPreviousCommandHandler implements UpdateHandler {

    private final CommandHandlerRegistry commandHandlerRegistry;
    private final ClientCommandStateRepository clientCommandStateRepository;
    @Autowired
    public OrderStepPreviousCommandHandler(@Lazy CommandHandlerRegistry commandHandlerRegistry, ClientCommandStateRepository clientCommandStateRepository) {
        this.commandHandlerRegistry = commandHandlerRegistry;
        this.clientCommandStateRepository = clientCommandStateRepository;
    }

    @Override
    public Command getCommand() {
        return Command.ORDER_STEP_PREVIOUS;
    }

    @Override
    public boolean canHandleUpdate(Update update) {
        return update.hasMessage() &&
                update.getMessage().hasText() &&
                update.getMessage().getText().startsWith(Button.ORDER_STEP_PREVIOUS.getAlias());
    }

    @Override
    public void handleUpdate(AbsSender absSender, Update update) throws TelegramApiException {
        Long chatId = update.getMessage().getChatId();

        executePreviousCommand(absSender, update, chatId);
    }

    private void executePreviousCommand(AbsSender absSender, Update update, Long chatId) throws TelegramApiException {
        Command command = clientCommandStateRepository.popByChatId(chatId);
        if (command == null) {
            return;
        }

        CommandHandler commandHandler = commandHandlerRegistry.find(command);
        commandHandler.executeCommand(absSender, update, chatId);
    }

}
