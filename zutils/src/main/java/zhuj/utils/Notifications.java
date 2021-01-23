package zhuj.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public class Notifications {

    private Notifications() {
    }

    // Notification  builder() {

    // }

    // public static NotificationChannel DefaultNotificationChannel(String id, String name){
    //     NotificationChannel channel = null;
    //     if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
    //         channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT);
    //         channel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
    //     }
    //     return channel;
    // }

    public static void createNotificationChannel(Context context, NotificationChannel channel) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
