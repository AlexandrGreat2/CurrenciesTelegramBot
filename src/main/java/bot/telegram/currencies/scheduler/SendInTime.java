package bot.telegram.currencies.scheduler;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class SendInTime {
    public static void sendMessageInTime(int hourOfTheDay){
        Calendar currentTime = Calendar.getInstance();
        Calendar scheduledTime = Calendar.getInstance();
        scheduledTime.set(Calendar.HOUR_OF_DAY, hourOfTheDay);
        scheduledTime.set(Calendar.MINUTE, 0);
        scheduledTime.set(Calendar.SECOND, 0);

        if (currentTime.after(scheduledTime)){
            scheduledTime.add(Calendar.DATE, 1);
        }
        Timer timer = new Timer();
        timer.schedule(timerTask, scheduledTime.getTime(), 86400000);
    }

    private static final TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            System.out.println("Send currency");//Task send at chosen time
        }
    };

    public static void main(String[] args) {
        sendMessageInTime(22);
    }
}
