package ru.skaldytskiyvladislav.delivery.handlers.commands.registries;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skaldytskiyvladislav.delivery.exceptions.HandlerNotFoundException;
import ru.skaldytskiyvladislav.delivery.handlers.CommandHandler;
import ru.skaldytskiyvladislav.delivery.models.domain.Command;

@Component
public class CommandHandlerRegistryDefault implements CommandHandlerRegistry {

    private Map<Command, CommandHandler> commandHandlers;

   @Autowired
    public void setCommandHandlers(List<CommandHandler> commandHandlers) {
        this.commandHandlers = commandHandlers.stream().collect(toMap(CommandHandler::getCommand, identity()));
    }

    @Override
    public CommandHandler find(Command command) {
        CommandHandler commandHandler = commandHandlers.get(command);
        if (commandHandler == null) {
            throw new HandlerNotFoundException("CommandHandler with name '" + command + "' not found");
        }
        return commandHandler;
    }

}
