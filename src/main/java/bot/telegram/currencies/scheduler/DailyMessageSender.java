package bot.telegram.currencies.scheduler;

import bot.telegram.currencies.db.Config;
import bot.telegram.currencies.io.TelegramBot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class DailyMessageSender {

    private static final String USERS_FOLDER = "src/users/";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final TelegramBot telegramBot = new TelegramBot();

    public static void processAllUserConfig() {
        File folder = new File(USERS_FOLDER);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    processUserConfig(file);
                }
            }
        }
    }

    private static void processUserConfig(File configFile) {
        try (FileReader reader = new FileReader(configFile)) {
            String userId = getUserIdFromFileName(configFile.getName());
            Config userConfig = gson.fromJson(reader, Config.class);
            var arrayOfStrings = userConfig.getNotificationTime().split(":");

            int notificationHour = -1;
            int notificationMinute = 0;
            if (arrayOfStrings[0] != null) {
                try {
                    notificationHour = Integer.parseInt(arrayOfStrings[0], 10);
                    notificationMinute = Integer.parseInt(arrayOfStrings[0], 10);
                } catch (RuntimeException ignored) {
                    System.out.println("Parsed time from config "+userId+" error. Try to check");
                }
            }

            if (notificationHour == -1) {
                return;
            }
            Calendar sendTime = Calendar.getInstance();
            sendTime.set(Calendar.HOUR_OF_DAY, notificationHour);
            sendTime.set(Calendar.MINUTE, notificationMinute);
            sendTime.set(Calendar.SECOND, 0);

            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    sendInformation(userId);
                }
            }, calculateDelayUntilNextSend(sendTime), 24 * 60 * 60 * 1000); // 24 години

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendInformation(String userId) {
        try {
            telegramBot.sendActualInformation(Long.valueOf(userId));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    private static String getUserIdFromFileName(String fileName) {
        String[] parts = fileName.split("_");
        return parts[0];
    }

    private static long calculateDelayUntilNextSend(Calendar sendTime) {
        long currentTimeMillis = System.currentTimeMillis();
        long sendTimeMillis = sendTime.getTimeInMillis();
        if (currentTimeMillis > sendTimeMillis) {
            sendTime.add(Calendar.DAY_OF_MONTH, 1);
            sendTimeMillis = sendTime.getTimeInMillis();
        }
        return sendTimeMillis - currentTimeMillis;
    }
}
