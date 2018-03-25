package io.malone.whattocook;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final String CHANNEL_ID = "COOKING_NOTIFICATIONS_CHANNEL";

    // https://developer.android.com/training/notify-user/channels.html
    private void setupNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            CharSequence name = getString(R.string.notification_channel_name);
            String description = getString(R.string.notification_channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(mChannel);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup the notification channel
        this.setupNotificationChannel();

        // Load data
        final ArrayList<Recipe> recipes = Recipe.getRecipesFromFile("recipes.json", this);

        // Setup button
        Button startCookingButton = findViewById(R.id.start_cooking_button);
        startCookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open SearchActivity with the loaded data
                Intent startSearchIntent = new Intent(MainActivity.this, SearchActivity.class);
                startSearchIntent.putParcelableArrayListExtra("recipes", recipes);
                startActivity(startSearchIntent);
            }
        });
    }
}
