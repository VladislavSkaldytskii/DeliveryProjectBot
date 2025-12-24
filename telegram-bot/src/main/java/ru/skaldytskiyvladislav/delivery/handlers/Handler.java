package ru.skaldytskiyvladislav.delivery.handlers;

import ru.skaldytskiyvladislav.delivery.models.domain.Command;

public interface Handler {

    Command getCommand();

}
