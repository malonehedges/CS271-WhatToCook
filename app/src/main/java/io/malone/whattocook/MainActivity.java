package io.malone.whattocook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Recipe> recipes = Recipe.getRecipiesFromFile("recipes.json", this);

        for (Recipe r : recipes) {
            System.out.println("" + r.getPrepTime() + " - " + r.prepTime);
        }

        // Setup button
        Button startCookingButton = findViewById(R.id.start_cooking_button);
        startCookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startSearchIntent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(startSearchIntent);
            }
        });
    }
}
