package ua.ivanzaitsev.bot.models.domain;

import static org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton.builder;

import java.util.Arrays;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

public enum Button {

    START("/start"),
    CATALOG("\uD83D\uDCE6 Каталог"),
    CART("\uD83D\uDECD Корзина"),
    SEND_PHONE_NUMBER("\uD83D\uDCF1 Нажмите на кнопку"),
    ORDER_STEP_NEXT("\u2714\uFE0F Подтвердить заказ"),
    ORDER_STEP_PREVIOUS("\u25C0 Назад"),
    ORDER_STEP_CANCEL("\u274C Отменить заказ"),
    ORDER_CONFIRM("\u2705 Ок");

    private final String alias;

    Button(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public static ReplyKeyboardMarkup createGeneralMenuKeyboard() {
        ReplyKeyboardMarkup.ReplyKeyboardMarkupBuilder keyboardBuilder = ReplyKeyboardMarkup.builder();
        keyboardBuilder.resizeKeyboard(true);
        keyboardBuilder.selective(true);

        keyboardBuilder.keyboardRow(new KeyboardRow(Arrays.asList(
                builder().text(CATALOG.getAlias()).build(),
                builder().text(CART.getAlias()).build()
                )));

        return keyboardBuilder.build();
    }

}
