package bot.telegram.currencies.io;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

import static bot.telegram.currencies.io.Constants.*;
import static bot.telegram.currencies.io.MessageUtil.*;

public class TelegramBot extends TelegramLongPollingBot {


    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message = new SendMessage();
        Long chatId = getChatId(update);
        //TODO: Потрібно придумати логіку як зберігати користувача та його chatId
        // Пропоную зробити клас 'User' де будуть зберігатися його налаштування

        if (update.hasMessage() && update.getMessage().getText().equalsIgnoreCase("/start")) {
            message.setChatId(chatId);
            message.setText(GREETINGS);
            createGreetingsMarkup(message);
        }

        if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals(GET_INFORMATION)) {
            message.setChatId(chatId);
            //TODO: Потрібно щоб замість 'ACTUAL_INFORMATION' виводило курс за налаштуванням
            message.setText(ACTUAL_INFORMATION);
            createGreetingsMarkup(message);
        }

        if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals(SETTINGS)) {
            message.setChatId(chatId);
            message.setText(SETTINGS);
            createSettingsMarkup(message);
        }

        if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals(SETTINGS_1)) {
            String userInput = update.getCallbackQuery().getData();
            // TODO: Використати 'userInput' щоб налаштувати кількість знаків після коми - 'exchange package'
            message.setChatId(chatId);
            message.setText("Виберіть " + Character.toLowerCase(SETTINGS_1.charAt(0)) + SETTINGS_1.substring(1) + ":");
            createSettingOneMarkup(message);
        }

        if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals(SETTINGS_2)) {
            String userInput = update.getCallbackQuery().getData();
            // TODO: Використати 'userInput' щоб обрати банк - 'exchange package'
            message.setChatId(chatId);
            message.setText("Виберіть " + Character.toLowerCase(SETTINGS_2.charAt(0)) + SETTINGS_2.substring(1) + ":");
            createSettingTwoMarkup(message);
        }

        if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals(SETTINGS_3)) {
            String userInput = update.getCallbackQuery().getData();
            // TODO: Використати 'userInput' щоб обрати валюту - 'exchange package'
            message.setChatId(chatId);
            message.setText("Виберіть " + Character.toLowerCase(SETTINGS_3.charAt(0)) + SETTINGS_3.substring(1) + ":");
            createSettingThreeMarkup(message);
        }

        if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals(SETTINGS_4)) {
            String userInput = update.getCallbackQuery().getData();
            // TODO: Використати 'userInput' щоб налаштувати - 'scheduler package'
            message.setChatId(chatId);
            message.setText("Виберіть " + Character.toLowerCase(SETTINGS_4.charAt(0)) + SETTINGS_4.substring(1) + ":");
            createSettingFourMarkup(message);
        }

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    private Long getChatId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getFrom().getId();
        }
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom().getId();
        }
        return null;
    }

    private void createGreetingsMarkup(SendMessage message) {
        Map<String, String> buttons = new HashMap<>();
        buttons.put(GET_INFORMATION, GET_INFORMATION);
        buttons.put(SETTINGS, SETTINGS);
        attachKeyboard(message, buttons);
    }

    private void createSettingsMarkup(SendMessage message) {
        Map<String, String> buttons = new HashMap<>();
        buttons.put(SETTINGS_1, SETTINGS_1);
        buttons.put(SETTINGS_2, SETTINGS_2);
        buttons.put(SETTINGS_3, SETTINGS_3);
        buttons.put(SETTINGS_4, SETTINGS_4);
        attachKeyboard(message, buttons);
    }



    private void createSettingOneMarkup(SendMessage message) {
        Map<String, String> buttons = new HashMap<>();
        buttons.put(SETTINGS_1_1, SETTINGS_1_1);
        buttons.put(SETTINGS_1_2, SETTINGS_1_2);
        buttons.put(SETTINGS_1_3, SETTINGS_1_3);
        attachKeyboard(message, buttons);
    }

    private void createSettingTwoMarkup(SendMessage message) {
        Map<String, String> buttons = new HashMap<>();
        buttons.put(SETTINGS_2_1, SETTINGS_2_1);
        buttons.put(SETTINGS_2_2, SETTINGS_2_2);
        buttons.put(SETTINGS_2_3, SETTINGS_2_3);
        attachKeyboard(message, buttons);
    }

    private void createSettingThreeMarkup(SendMessage message) {
        Map<String, String> buttons = new HashMap<>();
        buttons.put(SETTINGS_3_1, SETTINGS_3_1);
        buttons.put(SETTINGS_3_2, SETTINGS_3_2);
        attachKeyboard(message, buttons);
    }

    private void createSettingFourMarkup(SendMessage message) {
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(SETTINGS_4_ARRAY.get(0));
        row1.add(SETTINGS_4_ARRAY.get(1));
        row1.add(SETTINGS_4_ARRAY.get(2));
        rows.add(row1);
        KeyboardRow row2 = new KeyboardRow();
        row2.add(SETTINGS_4_ARRAY.get(3));
        row2.add(SETTINGS_4_ARRAY.get(4));
        row2.add(SETTINGS_4_ARRAY.get(5));
        rows.add(row2);
        KeyboardRow row3 = new KeyboardRow();
        row3.add(SETTINGS_4_ARRAY.get(6));
        row3.add(SETTINGS_4_ARRAY.get(7));
        row3.add(SETTINGS_4_ARRAY.get(8));
        rows.add(row3);
        KeyboardRow row4 = new KeyboardRow();
        row4.add(SETTINGS_4_ARRAY.get(9));
        row4.add(SETTINGS_4_ARRAY.get(10));
        rows.add(row4);
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(rows);
        message.setReplyMarkup(keyboardMarkup);
    }

    private void attachKeyboard(SendMessage message, Map<String, String> buttons) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardButtons = new ArrayList<>();
        for (String buttonName: buttons.keySet()) {
            String buttonValue = buttons.get(buttonName);
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(buttonName);
            button.setCallbackData(buttonValue);
            keyboardButtons.add(List.of(button));
        }
        keyboardMarkup.setKeyboard(keyboardButtons);
        message.setReplyMarkup(keyboardMarkup);
    }
}
