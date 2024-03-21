package bot.telegram.currencies.message;

public class PrintingMessages1 {
    public static void main(String[] args) {
        String username = "User";
        String greetingMessage = MessageTemplates1.getGreetingMessage(username);
        String farewellMessage = MessageTemplates1.getFarewellTemplate(username);

        System.out.println(greetingMessage);
        System.out.println(farewellMessage);
    }
}
