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

    public static ArrayList<Recipe> getRecipiesFromFile(String filename, Context context){
        ArrayList<Recipe> movieList = new ArrayList<>();

        // try to read from JSON file
        // get information by using the tags
        // construct a Movie Object for each movie in JSON
        // add the object to ArrayList
        // return ArrayList
        try {
            String jsonString = loadJSONStringFromAsset(filename, context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray recipeJSONArray = json.getJSONArray("recipes");

            // for loop to go through each movie in the movies.json file
            for (int i = 0; i < recipeJSONArray.length(); i++) {
                JSONObject recipeJSON = recipeJSONArray.getJSONObject(i);

                Recipe movie = new Recipe();
//                movie.title = movieJSON.getString("title");
//                movie.episodeNumber = movieJSON.getString("episode_number");
//
//                // handle array of characters
//                JSONArray mainCharactersJSONArray = movieJSON.getJSONArray("main_characters");
//                String[] mainCharacters = new String[mainCharactersJSONArray.length()];
//                for (int j = 0; j < mainCharactersJSONArray.length(); j++) {
//                    mainCharacters[j] = mainCharactersJSONArray.getString(j);
//                }
//                movie.mainCharacters = mainCharacters;
//
//                movie.description = movieJSON.getString("description");
//                movie.poster = movieJSON.getString("poster");
//                movie.url = movieJSON.getString("url");

                // add to ArrayList
                movieList.add(movie);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieList;
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
