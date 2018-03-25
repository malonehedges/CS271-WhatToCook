package io.malone.whattocook;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends BaseAdapter {

    private Context context;
    private List<Recipe> recipeList;

    private LayoutInflater inflater;

    public RecipeAdapter(Context context, List<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.recipeList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.recipeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // check if the view already exists
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.list_item_recipe, parent, false);

            // add the views to the holder
            holder = new ViewHolder();
            holder.thumbnail = convertView.findViewById(R.id.recipe_list_item_thumbnail);
            holder.title = convertView.findViewById(R.id.recipe_list_item_title);
            holder.detailText = convertView.findViewById(R.id.recipe_list_item_detail_text);
            holder.cookButton = convertView.findViewById(R.id.recipe_list_item_cook_button);

            // add the holder to the view
            convertView.setTag(holder);
        } else {
            // get the view holder from convertView
            holder = (ViewHolder) convertView.getTag();
        }

        // get the movie for this row
        final Recipe recipe = (Recipe) getItem(position);

        // get relative subview of the row view
        ImageView thumbnail = holder.thumbnail;
        TextView title = holder.title;
        TextView detailText = holder.detailText;
        Button cookButton = holder.cookButton;

        // use Picasso library to load image from the image url
        Picasso.with(this.context).load(recipe.image).into(thumbnail);
        System.out.println(recipe.image);

        title.setText(recipe.title);

        String pluralDetailText = parent.getResources().getQuantityString(
                R.plurals.list_item_detail,
                recipe.servings,
                recipe.servings,
                recipe.prepTime
        );
        detailText.setText(pluralDetailText);
        detailText.setTextColor(Color.BLUE);

        cookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // https://developer.android.com/training/notify-user/build-notification.html

                // Create an explicit intent for an Activity in your app
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(recipe.url));
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

                String contentTitle = "Cooking Instructions";
                String contentText = "The instructions for " + recipe.title + " can be found here!";

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, MainActivity.CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle(contentTitle)
                        .setContentText(contentText)
                        // Use big text style
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(contentText))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                // notificationId is a unique int for each notification
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                notificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        ImageView thumbnail;
        TextView title;
        TextView detailText;
        Button cookButton;
    }
}
