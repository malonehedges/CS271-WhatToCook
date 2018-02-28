package io.malone.whattocook;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Recipe {

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
     * 0: under 30 minutes
     * 1: 30 minutes - 1 hour
     * 2: over 1 hour
     */
    public int getPrepTime() {
        String[] split = this.prepTime.split(" ");
        int firstNum = Integer.parseInt(split[0]);

        // TODO: Find out what category "1 hour" is in

        // first character of second "word" will either be 'h' or 'm'
        if (split[1].charAt(0) == 'h') {
            return 2;
        } else {
            return firstNum < 30 ? 0 : 1;
        }
    }

    public static ArrayList<Recipe> getRecipiesFromFile(String filename, Context context){
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
}
