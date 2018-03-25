package io.malone.whattocook;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Recipe implements Parcelable {

    // Referenced below, static variables here for readability
    public static final int UNDER_30_MINS = 0;
    public static final int HALF_HOUR_TO_1_HOUR = 1;
    public static final int OVER_1_HOUR = 2;

    String title;
    String image;
    String url;
    String description;
    int servings;
    String prepTime;
    String dietLabel;

    public Recipe(String title, String image, String url, String description, int servings, String prepTime, String dietLabel) {
        this.title = title;
        this.image = image;
        this.url = url;
        this.description = description;
        this.servings = servings;
        this.prepTime = prepTime;
        this.dietLabel = dietLabel;
    }

    /**
     * UNDER_30_MINS (0): under 30 minutes
     * HALF_HOUR_TO_1_HOUR (1): 30 minutes - 1 hour
     * OVER_1_HOUR (2): over 1 hour
     */
    public int getPrepTime() {
        String[] split = this.prepTime.split(" ");
        int firstNum = Integer.parseInt(split[0]);

        // first character of second "word" will either be 'h' or 'm'
        // NOTE: 1 hour or longer goes into "over 1 hour"
        if (split[1].charAt(0) == 'h') {
            return Recipe.OVER_1_HOUR;
        } else {
            return firstNum < 30 ? Recipe.UNDER_30_MINS : Recipe.HALF_HOUR_TO_1_HOUR;
        }
    }

    public static ArrayList<Recipe> getRecipesFromFile(String filename, Context context){
        ArrayList<Recipe> recipeList = new ArrayList<>();

        try {
            String jsonString = loadJSONStringFromAsset(filename, context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray recipeJSONArray = json.getJSONArray("recipes");

            for (int i = 0; i < recipeJSONArray.length(); i++) {
                JSONObject recipeJSON = recipeJSONArray.getJSONObject(i);
                recipeList.add(Recipe.fromJSON(recipeJSON));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recipeList;
    }

    private static Recipe fromJSON(JSONObject json) {
        try {
            String title = json.getString("title");
            String image = json.getString("image");
            String url = json.getString("url");
            String description = json.getString("description");
            int servings = json.getInt("servings");
            String prepTime = json.getString("prepTime");
            String dietLabel = json.getString("dietLabel");

            return new Recipe(title, image, url, description, servings, prepTime, dietLabel);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    // helper method that loads from any Json file
    private static String loadJSONStringFromAsset(String filename, Context context) {
        String json = null;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

    @Override
    public String toString() {
        return "Recipe {"
                + "title: " + this.title
                + ", servings: " + this.servings
                + ", prepTime: " + this.prepTime
                + ", dietLabel: " + this.dietLabel
                + "}";
    }

    // Parcelable Methods

    protected Recipe(Parcel in) {
        title = in.readString();
        image = in.readString();
        url = in.readString();
        description = in.readString();
        servings = in.readInt();
        prepTime = in.readString();
        dietLabel = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(url);
        dest.writeString(description);
        dest.writeInt(servings);
        dest.writeString(prepTime);
        dest.writeString(dietLabel);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
