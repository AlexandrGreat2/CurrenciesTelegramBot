package bot.telegram.currencies.io;

import bot.telegram.currencies.db.BankExchangeRates;
import bot.telegram.currencies.db.CurrencyRateDataHelper;
import bot.telegram.currencies.db.TelegramUser;
import bot.telegram.currencies.db.UserConfigDataHelper;
import bot.telegram.currencies.message.MessageTemplate;
import bot.telegram.currencies.message.MessageTemplateENG;
import bot.telegram.currencies.message.MessageTemplateUKR;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class TelegramBot extends TelegramLongPollingBot {

    BotConfig botConfig = BotConfig.loadBotConfig();
    ConcurrentHashMap<Long, TelegramUser> currentUsers = new ConcurrentHashMap<>();
    ConcurrentHashMap<Long, MessageTemplate> currentLanguage = new ConcurrentHashMap<>();
    TelegramUser currentUser;

    public void sendActualInformation(Long chatId){
        SendMessage message = new SendMessage();
        BankExchangeRates bankExchangeRates = CurrencyRateDataHelper.loadCurrencyRates();
        message.setText(currentLanguage.get(chatId).getActualInformation(currentUsers.get(chatId), bankExchangeRates));
        createGreetingsMarkup(message, chatId);
        System.out.println("send to chat "+chatId);
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message = new SendMessage();
        Long chatId = getChatId(update);

        if (update.hasMessage() && update.getMessage().getText().equalsIgnoreCase("/start")) {
            currentUser = UserConfigDataHelper.loadUserConfig(chatId);
            UserConfigDataHelper.saveUserConfig(currentUser);
            currentUsers.put(chatId, currentUser);
            changeLanguageTemplate(chatId);
            message.setText(currentLanguage.get(chatId).getGreetings());
            createGreetingsMarkup(message, chatId);
        }

        if (update.hasMessage() && update.getMessage().getText().equalsIgnoreCase("/config")) {
            message.setChatId(chatId);
            message.setText(currentLanguage.get(chatId).showUserSettings(currentUsers.get(chatId)));
        }

        if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals(currentLanguage.get(chatId).getInformation())) {
            BankExchangeRates bankExchangeRates = CurrencyRateDataHelper.loadCurrencyRates();
            message.setText(currentLanguage.get(chatId).getActualInformation(currentUsers.get(chatId), bankExchangeRates));
            createGreetingsMarkup(message, chatId);
        }

        if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals(currentLanguage.get(chatId).getSettings())) {
            message.setText(currentLanguage.get(chatId).getSettings());
            createSettingsMarkup(message, chatId);
        }

        if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals(currentLanguage.get(chatId).getSettingsDecimalPlaceTitle())) {
            createDecimalPlaceSettingsMarkup(message, chatId, currentUsers.get(chatId));
        }

        if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals(currentLanguage.get(chatId).getSettingsBankTitle())) {
            createBankSettingsMarkup(message, chatId, currentUsers.get(chatId));
        }

        if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals(currentLanguage.get(chatId).getSettingsCurrenciesTitle())) {
            createCurrenciesSettingsMarkup(message, chatId, currentUsers.get(chatId));
        }

        if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals(currentLanguage.get(chatId).getSettingsNotificationTimeTitle())) {
            createNotificationTimeSettingsMarkup(message, chatId);
        }

        if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals(currentLanguage.get(chatId).getLanguageTitle())) {
            createLanguageMarkup(message, chatId, currentUsers.get(chatId));
        }


        manageUserInput(update, message, chatId);


        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
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
        buttons.put(currentLanguage.get(chatId).getInformation(), currentLanguage.get(chatId).getInformation());
        buttons.put(currentLanguage.get(chatId).getSettings(), currentLanguage.get(chatId).getSettings());
        attachKeyboard(message, buttons);
    }

    private void createSettingsMarkup(SendMessage message, Long chatId) {
        message.setChatId(chatId);
        Map<String, String> buttons = new HashMap<>();
        buttons.put(currentLanguage.get(chatId).getSettingsDecimalPlaceTitle(), currentLanguage.get(chatId).getSettingsDecimalPlaceTitle());
        buttons.put(currentLanguage.get(chatId).getSettingsBankTitle(), currentLanguage.get(chatId).getSettingsBankTitle());
        buttons.put(currentLanguage.get(chatId).getSettingsCurrenciesTitle(), currentLanguage.get(chatId).getSettingsCurrenciesTitle());
        buttons.put(currentLanguage.get(chatId).getSettingsNotificationTimeTitle(), currentLanguage.get(chatId).getSettingsNotificationTimeTitle());
        buttons.put(currentLanguage.get(chatId).getLanguageTitle(), currentLanguage.get(chatId).getLanguageTitle());
        attachKeyboard(message, buttons);
    }

    private void createDecimalPlaceSettingsMarkup(SendMessage message, Long chatId, TelegramUser user) {
        message.setChatId(chatId);
        message.setText(currentLanguage.get(chatId).getChoose() +
                Character.toLowerCase(currentLanguage.get(chatId).getSettingsDecimalPlaceTitle().charAt(0)) +
                currentLanguage.get(chatId).getSettingsDecimalPlaceTitle().substring(1) + ":");
        Map<String, String> buttons = new HashMap<>();
        List<String> decimalPlaceSettings = currentLanguage.get(chatId).setDecimalPlaceSettings(user);
        for (int i = 0; i < decimalPlaceSettings.size(); i++) {
            buttons.put(decimalPlaceSettings.get(i), "SETTINGS_1_" + (i + 1));
        }
        attachKeyboard(message, buttons);
    }

    private void createBankSettingsMarkup(SendMessage message, Long chatId, TelegramUser user) {
        message.setChatId(chatId);
        message.setText(currentLanguage.get(chatId).getChoose() +
                Character.toLowerCase(currentLanguage.get(chatId).getSettingsBankTitle().charAt(0)) +
                currentLanguage.get(chatId).getSettingsBankTitle().substring(1) + ":");
        Map<String, String> buttons = new HashMap<>();
        List<String> bankSettings = currentLanguage.get(chatId).setBankSettings(user);
        for (int i = 0; i < bankSettings.size(); i++) {
            buttons.put(bankSettings.get(i), "SETTINGS_2_" + (i + 1));
        }
        attachKeyboard(message, buttons);
    }

    private void createCurrenciesSettingsMarkup(SendMessage message, Long chatId, TelegramUser user) {
        message.setChatId(chatId);
        message.setText(currentLanguage.get(chatId).getChoose() +
                Character.toLowerCase(currentLanguage.get(chatId).getSettingsCurrenciesTitle().charAt(0)) +
                currentLanguage.get(chatId).getSettingsCurrenciesTitle().substring(1) + ":");
        Map<String, String> buttons = new HashMap<>();
        List<String> currenciesSettings = currentLanguage.get(chatId).setCurrenciesSettings(user);
        for (int i = 0; i < currenciesSettings.size(); i++) {
            buttons.put(currenciesSettings.get(i), "SETTINGS_3_" + (i + 1));
        }
        attachKeyboard(message, buttons);
    }

    private void createNotificationTimeSettingsMarkup(SendMessage message, Long chatId) {
        message.setChatId(chatId);
        message.setText(currentLanguage.get(chatId).getChoose() +
                Character.toLowerCase(currentLanguage.get(chatId).getSettingsNotificationTimeTitle().charAt(0)) +
                currentLanguage.get(chatId).getSettingsNotificationTimeTitle().substring(1) + ":");
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(currentLanguage.get(chatId).getNotificationTimeSettings().get(0));
        row1.add(currentLanguage.get(chatId).getNotificationTimeSettings().get(1));
        row1.add(currentLanguage.get(chatId).getNotificationTimeSettings().get(2));
        rows.add(row1);
        KeyboardRow row2 = new KeyboardRow();
        row2.add(currentLanguage.get(chatId).getNotificationTimeSettings().get(3));
        row2.add(currentLanguage.get(chatId).getNotificationTimeSettings().get(4));
        row2.add(currentLanguage.get(chatId).getNotificationTimeSettings().get(5));
        rows.add(row2);
        KeyboardRow row3 = new KeyboardRow();
        row3.add(currentLanguage.get(chatId).getNotificationTimeSettings().get(6));
        row3.add(currentLanguage.get(chatId).getNotificationTimeSettings().get(7));
        row3.add(currentLanguage.get(chatId).getNotificationTimeSettings().get(8));
        rows.add(row3);
        KeyboardRow row4 = new KeyboardRow();
        row4.add(currentLanguage.get(chatId).getNotificationTimeSettings().get(9));
        row4.add(currentLanguage.get(chatId).getNotificationTimeSettings().get(10));
        rows.add(row4);
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(rows);
        message.setReplyMarkup(keyboardMarkup);
    }

    private void createLanguageMarkup(SendMessage message, Long chatId, TelegramUser user) {
        message.setChatId(chatId);
        message.setText(currentLanguage.get(chatId).getChoose() +
                Character.toLowerCase(currentLanguage.get(chatId).getLanguageTitle().charAt(0)) +
                currentLanguage.get(chatId).getLanguageTitle().substring(1) + ":");
        Map<String, String> buttons = new HashMap<>();
        List<String> languageSettings = currentLanguage.get(chatId).setLanguageSettings(user);
        for (int i = 0; i < languageSettings.size(); i++) {
            buttons.put(languageSettings.get(i), "SETTINGS_5_" + (i + 1));
        }
        attachKeyboard(message, buttons);
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
                userInput = assignSettings(dataFromUser, getChatId(update));
            }
        }
        if (update.hasMessage() && !update.getMessage().getText().equalsIgnoreCase("/start")) {
            String dataFromUser = update.getMessage().getText();
            userInput = assignSettings(dataFromUser, getChatId(update));
            if (!currentLanguage.get(getChatId(update)).getNotificationTimeSettings().contains(userInput.get("SETTINGS_4"))) {
                userInput = new HashMap<>();
            }
        }
        return userInput;
    }

    private Map<String, String> assignSettings(String dataFromUser, long chatId) {
        Map<String, String> result = new HashMap<>();
        switch (dataFromUser) {
            case "SETTINGS_1_1" : result.put("SETTINGS_1", currentLanguage.get(chatId).getDecimalPlaceSettings().get(0));
                break;
            case "SETTINGS_1_2" : result.put("SETTINGS_1", currentLanguage.get(chatId).getDecimalPlaceSettings().get(1));
                break;
            case "SETTINGS_1_3" : result.put("SETTINGS_1", currentLanguage.get(chatId).getDecimalPlaceSettings().get(2));
                break;
            case "SETTINGS_2_1" : result.put("SETTINGS_2", currentLanguage.get(chatId).getBankSettings().get(0));
                break;
            case "SETTINGS_2_2" : result.put("SETTINGS_2", currentLanguage.get(chatId).getBankSettings().get(1));
                break;
            case "SETTINGS_2_3" : result.put("SETTINGS_2", currentLanguage.get(chatId).getBankSettings().get(2));
                break;
            case "SETTINGS_3_1" : result.put("SETTINGS_3", currentLanguage.get(chatId).getCurrenciesSettings().get(0));
                break;
            case "SETTINGS_3_2" : result.put("SETTINGS_3", currentLanguage.get(chatId).getCurrenciesSettings().get(1));
                break;
            case "SETTINGS_5_1" : result.put("SETTINGS_5", currentLanguage.get(chatId).getLanguageSettings().get(0));
                break;
            case "SETTINGS_5_2" : result.put("SETTINGS_5", currentLanguage.get(chatId).getLanguageSettings().get(1));
                break;
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

    private void manageUserInput(Update update, SendMessage message, Long chatId) {
        Map<String, String> userInput = getUserInput(update);
        String key = "";
        String value = "";
        for (Map.Entry<String, String> entry : userInput.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
        }
        if (key.equals("SETTINGS_1")) {
            currentUsers.get(chatId).setDecimalPlaces(value);
            UserConfigDataHelper.saveUserConfig(currentUsers.get(chatId));
            deleteMessage(update, chatId);
            createDecimalPlaceSettingsMarkup(message, chatId, currentUsers.get(chatId));
        }
        if (key.equals("SETTINGS_2")) {
            currentUsers.get(chatId).setBank(value);
            UserConfigDataHelper.saveUserConfig(currentUsers.get(chatId));
            deleteMessage(update, chatId);
            createBankSettingsMarkup(message, chatId, currentUsers.get(chatId));
        }
        if (key.equals("SETTINGS_3")) {
            List<String> userCurrencies = new ArrayList<>();
            for (String currency : currentUsers.get(chatId).getCurrencies()) {
                userCurrencies.add(currency);
            }
            if (userCurrencies.contains(value)) {
                userCurrencies.remove(value);
            } else {
                userCurrencies.add(value);
            }
            currentUsers.get(chatId).setCurrencies(userCurrencies);
            UserConfigDataHelper.saveUserConfig(currentUsers.get(chatId));
            deleteMessage(update, chatId);
            createCurrenciesSettingsMarkup(message, chatId, currentUsers.get(chatId));
        }
        if (key.equals("SETTINGS_4")) {
            currentUsers.get(chatId).setNotificationTime(value);
        }
        if (key.equals("SETTINGS_5")) {
            currentUsers.get(chatId).setLanguage(value);
            changeLanguageTemplate(chatId);
            UserConfigDataHelper.saveUserConfig(currentUsers.get(chatId));
            deleteMessage(update, chatId);
//            createLanguageMarkup(message, chatId, currentUsers.get(chatId));
            message.setText(currentLanguage.get(chatId).getGreetings());
            createGreetingsMarkup(message, chatId);
        }
    }
    public void changeLanguageTemplate(long chatId) {
        TelegramUser user = currentUsers.get(chatId);
        if (user.getLanguage().equals(new String("Ukrainian".getBytes(), StandardCharsets.UTF_8))) {
            currentLanguage.put(chatId, new MessageTemplateUKR());
        }
        if (user.getLanguage().equals(new String("English".getBytes(), StandardCharsets.UTF_8))) {
            currentLanguage.put(chatId, new MessageTemplateENG());
        }
    }
}

