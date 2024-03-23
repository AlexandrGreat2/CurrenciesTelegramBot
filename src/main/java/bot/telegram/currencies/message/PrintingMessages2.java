package bot.telegram.currencies.message;

public class PrintingMessages2 {
    public static void main(String[] args) {

        String mainMenu = MessageTemplates2.getMainMenu();
        System.out.println(mainMenu);

        String infoMessage = MessageTemplates2.getInfoMessage();
        System.out.println(infoMessage);

        String settingsMessage = MessageTemplates2.getSettingsMessage();
        System.out.println(settingsMessage);
    }
}
