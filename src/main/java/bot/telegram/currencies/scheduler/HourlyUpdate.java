package bot.telegram.currencies.scheduler;

import java.util.Timer;
import java.util.TimerTask;

public class HourlyUpdate {
    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("null");//вставити метод, який буде реквест дані кожну годину
            }
        }, 0, 60*60*1000);
    }
}
