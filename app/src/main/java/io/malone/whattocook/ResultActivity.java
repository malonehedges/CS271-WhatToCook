package io.malone.whattocook;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView resultText = findViewById(R.id.result_count_text);
        ListView resultList = findViewById(R.id.result_list);

        // TODO
        resultList.setAdapter(new RecipeAdapter(this, null));

        this.createNotification();
    }

    private void createNotification() {
        System.out.println("howdy ho");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("my_shit", "fun stuff right", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }

        Intent notificationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        String notificationText = "The instructions for " + "food here" + " can be found here!";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "my_shit")
                .setContentTitle("Cooking Instruction")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationText))
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(contentIntent)
                .setAutoCancel(true);

        final NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(970970, builder.build());
    }
}
