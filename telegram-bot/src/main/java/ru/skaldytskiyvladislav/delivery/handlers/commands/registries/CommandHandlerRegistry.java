package ru.skaldytskiyvladislav.delivery.handlers.commands.registries;

import java.util.List;

import ru.skaldytskiyvladislav.delivery.handlers.CommandHandler;
import ru.skaldytskiyvladislav.delivery.models.domain.Command;

public interface CommandHandlerRegistry {

    void setCommandHandlers(List<CommandHandler> commandHandlers);

    CommandHandler find(Command command);

}
