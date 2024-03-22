package bot.telegram.currencies.io;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static bot.telegram.currencies.io.Constants.*;
import static bot.telegram.currencies.io.MessageUtil.*;

public class TelegramBot extends TelegramLongPollingBot {


    UserUtil randomUser = new UserUtil("2", "ПриватБанк", List.of("EUR"), "10:00");
    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message = new SendMessage();
        Long chatId = getChatId(update);
        //TODO: Потрібно придумати логіку як зберігати користувача та його chatId
        // Пропоную зробити клас 'User' де будуть зберігатися його налаштування

        if (update.hasMessage() && update.getMessage().getText().equalsIgnoreCase("/start")) {
            message.setText(GREETINGS);
            createGreetingsMarkup(message, chatId);
        }

        if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals(GET_INFORMATION)) {
            //TODO: Потрібно щоб замість 'ACTUAL_INFORMATION' виводило курс за налаштуванням
            message.setText(ACTUAL_INFORMATION);
            createGreetingsMarkup(message, chatId);
        }

        if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals(SETTINGS)) {
            message.setText(SETTINGS);
            createSettingsMarkup(message, chatId);
        }

        if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals(SETTINGS_DECIMAL_PLACE_TITLE)) {
            createDecimalPlaceSettingsMarkup(message, chatId, randomUser);
        }

        if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals(SETTINGS_BANK_TITLE)) {
            createBankSettingsMarkup(message, chatId, randomUser);
        }

        if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals(SETTINGS_CURRENCIES_TITLE)) {
            createCurrenciesSettingsMarkup(message, chatId, randomUser);
        }

        if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals(SETTINGS_NOTIFICATION_TIME_TITLE)) {
            createNotificationTimeSettingsMarkup(message, chatId);
        }

        Map<String, String> userInput = getUserInput(update);
        System.out.println("userInput = " + userInput);
        String key = "";
        String value = "";
        for (Map.Entry<String, String> entry : userInput.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
        }
        if (key.equals("SETTINGS_1")) {
            // TODO: Використати 'userInput' щоб налаштувати кількість знаків після коми - 'exchange package'
            randomUser.setDecimalPlaces(value);
            deleteMessage(update, chatId);
            createDecimalPlaceSettingsMarkup(message, chatId, randomUser);
        }
        if (key.equals("SETTINGS_2")) {
            // TODO: Використати 'userInput' щоб обрати банк - 'exchange package'
            randomUser.setBank(value);
            deleteMessage(update, chatId);
            createBankSettingsMarkup(message, chatId, randomUser);
        }
        if (key.equals("SETTINGS_3")) {
            // TODO: Використати 'value' щоб обрати валюту - 'exchange package'
            List<String> userCurrencies = new ArrayList<>(randomUser.getCurrencies());
            if (userCurrencies.contains(value)) {
                userCurrencies.remove(value);
            } else {
                userCurrencies.add(value);
            }
            randomUser.setCurrencies(userCurrencies);
            deleteMessage(update, chatId);
            createCurrenciesSettingsMarkup(message, chatId, randomUser);
        }
        if (key.equals("SETTINGS_4")) {
            // TODO: Використати 'value' щоб налаштувати - 'scheduler package'
            System.out.println("value = " + value);
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

    private void createGreetingsMarkup(SendMessage message, Long chatId) {
        message.setChatId(chatId);
        Map<String, String> buttons = new HashMap<>();
        buttons.put(GET_INFORMATION, GET_INFORMATION);
        buttons.put(SETTINGS, SETTINGS);
        attachKeyboard(message, buttons);
    }

    private void createSettingsMarkup(SendMessage message, Long chatId) {
        message.setChatId(chatId);
        Map<String, String> buttons = new HashMap<>();
        buttons.put(SETTINGS_DECIMAL_PLACE_TITLE, SETTINGS_DECIMAL_PLACE_TITLE);
        buttons.put(SETTINGS_BANK_TITLE, SETTINGS_BANK_TITLE);
        buttons.put(SETTINGS_CURRENCIES_TITLE, SETTINGS_CURRENCIES_TITLE);
        buttons.put(SETTINGS_NOTIFICATION_TIME_TITLE, SETTINGS_NOTIFICATION_TIME_TITLE);
        attachKeyboard(message, buttons);
    }



    private void createDecimalPlaceSettingsMarkup(SendMessage message, Long chatId, UserUtil user) {
        message.setChatId(chatId);
        message.setText(new String("Виберіть ".getBytes(), StandardCharsets.UTF_8) + Character.toLowerCase(SETTINGS_DECIMAL_PLACE_TITLE.charAt(0)) + SETTINGS_DECIMAL_PLACE_TITLE.substring(1) + ":");
        Map<String, String> buttons = new HashMap<>();
        List<String> decimalPlaceSettings = setDecimalPlaceSettings(user);
        for (int i = 0; i < decimalPlaceSettings.size(); i++) {
            buttons.put(decimalPlaceSettings.get(i), "SETTINGS_1_" + (i + 1));
        }
        attachKeyboard(message, buttons);
    }

    private void createBankSettingsMarkup(SendMessage message, Long chatId, UserUtil user) {
        message.setChatId(chatId);
        message.setText(new String("Виберіть ".getBytes(), StandardCharsets.UTF_8) + Character.toLowerCase(SETTINGS_BANK_TITLE.charAt(0)) + SETTINGS_BANK_TITLE.substring(1) + ":");
        Map<String, String> buttons = new HashMap<>();
        List<String> bankSettings = setBankSettings(user);
        for (int i = 0; i < bankSettings.size(); i++) {
            buttons.put(bankSettings.get(i), "SETTINGS_2_" + (i + 1));
        }
        attachKeyboard(message, buttons);
    }

    private void createCurrenciesSettingsMarkup(SendMessage message, Long chatId, UserUtil user) {
        message.setChatId(chatId);
        message.setText(new String("Виберіть ".getBytes(), StandardCharsets.UTF_8) + Character.toLowerCase(SETTINGS_CURRENCIES_TITLE.charAt(0)) + SETTINGS_CURRENCIES_TITLE.substring(1) + ":");
        Map<String, String> buttons = new HashMap<>();
        List<String> currenciesSettings = setCurrenciesSettings(user);
        for (int i = 0; i < currenciesSettings.size(); i++) {
            buttons.put(currenciesSettings.get(i), "SETTINGS_3_" + (i + 1));
        }
        attachKeyboard(message, buttons);
    }

    private void createNotificationTimeSettingsMarkup(SendMessage message, Long chatId) {
        message.setChatId(chatId);
        message.setText(new String("Виберіть ".getBytes(), StandardCharsets.UTF_8) + Character.toLowerCase(SETTINGS_NOTIFICATION_TIME_TITLE.charAt(0)) + SETTINGS_NOTIFICATION_TIME_TITLE.substring(1) + ":");
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

    private Map<String, String> getUserInput(Update update) {
        Map<String, String> userInput = new HashMap<>();
        if (update.hasCallbackQuery()) {
            if (update.getCallbackQuery().getData().startsWith("SETTINGS")) {
                String dataFromUser = update.getCallbackQuery().getData();
                userInput = assignSettings(dataFromUser);
            }
        }
        if (update.hasMessage() && !update.getMessage().getText().equalsIgnoreCase("/start")) {
            String dataFromUser = update.getMessage().getText();
            userInput = assignSettings(dataFromUser);
            if (!SETTINGS_4_ARRAY.contains(userInput.get("SETTINGS_4"))) {
                userInput = new HashMap<>();
            }
        }
        return userInput;
    }

    private Map<String, String> assignSettings(String dataFromUser) {
        Map<String, String> result = new HashMap<>();
        switch (dataFromUser) {
            case "SETTINGS_1_1" : result.put("SETTINGS_1", "2");
                break;
            case "SETTINGS_1_2" : result.put("SETTINGS_1", "3");
                break;
            case "SETTINGS_1_3" : result.put("SETTINGS_1", "4");
                break;
            case "SETTINGS_2_1" : result.put("SETTINGS_2", "НБУ");
                break;
            case "SETTINGS_2_2" : result.put("SETTINGS_2", "ПриватБанк");
                break;
            case "SETTINGS_2_3" : result.put("SETTINGS_2", "Монобанк");
                break;
            case "SETTINGS_3_1" : result.put("SETTINGS_3", "USD");
                break;
            case "SETTINGS_3_2" : result.put("SETTINGS_3", "EUR");
                break;
            // "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "Вимкнути повідомлення"
            default: result.put("SETTINGS_4", dataFromUser);
        }
        return result;
    }

    private void deleteMessage(Update update, Long chatId) {
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        DeleteMessage deleteMessage = new DeleteMessage(chatId.toString(), messageId);
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
