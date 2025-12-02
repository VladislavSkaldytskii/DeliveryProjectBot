package ua.ivanzaitsev.bot.handlers.commands.registries;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import ua.ivanzaitsev.bot.exceptions.HandlerNotFoundException;
import ua.ivanzaitsev.bot.handlers.CommandHandler;
import ua.ivanzaitsev.bot.models.domain.Command;

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
