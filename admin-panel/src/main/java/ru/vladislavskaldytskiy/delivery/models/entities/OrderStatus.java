package ru.vladislavskaldytskiy.delivery.models.entities;

public enum OrderStatus {

    WAITING("В ожидании"),
    PROCESSED("В процессе"),
    COMPLETED("Выполнен"),
    CANCELED("Отменен");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
