package bot.telegram.currencies.message;

public class MessageTemplates2 {

        public static String getMainMenu() {
            return "Привіт! Цей бот допоможе тобі відслідковувати актуальні курси валют!\nОберіть одну з наступних дій:\n1. Отримати інфо\n2. Налаштування";
        }

        public static String getInfoMessage() {
            return "Ось актуальна інформація про курси валют.";
        }

        public static String getSettingsMessage() {
            return "Ваші поточні налаштування:\nКількість знаків після коми: 2\nБанк: ПриватБанк\nВалюти: USD, EUR";
        }

    }
