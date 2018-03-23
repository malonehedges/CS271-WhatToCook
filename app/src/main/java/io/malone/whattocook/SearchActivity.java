package io.malone.whattocook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ArrayList<Recipe> recipes;

    private int dietChoice = 0;
    private int servingChoice = 0;
    private int preparationChoice = 0;

    private List<String> dietStrings = new ArrayList<>();
    private List<String> servingStrings = new ArrayList<>();
    private List<String> preparationStrings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        this.recipes = this.getIntent().getExtras().getParcelableArrayList("recipes");

        this.initializeSpinners();
        this.initializeButton();
    }

    private void initializeSpinners() {
        // Initialize Spinner options

        // Diet based on data from database
        this.dietStrings.add("");
        for (Recipe recipe : this.recipes) {
            if (!this.dietStrings.contains(recipe.dietLabel)) {
                this.dietStrings.add(recipe.dietLabel);
            }
        }

        // Static choices for serving size
        this.servingStrings.add("");
        this.servingStrings.add("less than 4"); // 1
        this.servingStrings.add("4-6"); // 2
        this.servingStrings.add("7-9"); // 3
        this.servingStrings.add("more than 10"); // 4

        // Static choices for preparation times
        this.preparationStrings.add("");
        this.preparationStrings.add("30 minutes or less"); // 1
        this.preparationStrings.add("less than 1 hour"); // 2
        this.preparationStrings.add("more than 1 hour"); // 3

        // Set the spinner adapters
        setSpinnerAdapter(R.id.diet_spinner, this.dietStrings);
        setSpinnerAdapter(R.id.serving_spinner, this.servingStrings);
        setSpinnerAdapter(R.id.preparation_spinner, this.preparationStrings);
    }

    void performSearch() {
        // Get the appropriate results based on the selected options
        ArrayList<Recipe> searchResults = new ArrayList<>();

        for (Recipe recipe : recipes) {
            boolean add = false;

            if (this.dietChoice > 0) {
                if (recipe.dietLabel.equals(this.dietStrings.get(this.dietChoice))) {
                    add = true;
                }
            }

            if (this.servingChoice > 0) {

            }

            if (this.preparationChoice > 0) {

            }

            if (add) {
                searchResults.add(recipe);
            }
        }

        System.out.println("----------------------------------------");
        System.out.println(dietChoice);
        System.out.println(servingChoice);
        System.out.println(preparationChoice);

        // Start the ResultActivity, passing the results
        Intent resultIntent = new Intent(this, ResultActivity.class);
        resultIntent.putParcelableArrayListExtra("results", searchResults);
        startActivity(resultIntent);
    }

    private void initializeButton() {
        Button submitButton = findViewById(R.id.search_recipies_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchActivity.this.performSearch();
            }
        });
    }

    private void setSpinnerAdapter(int viewId, List<String> options) {
        Spinner spinner = findViewById(viewId);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                options
        );
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.diet_spinner:
                this.dietChoice = position;
                break;
            case R.id.serving_spinner:
                this.servingChoice = position;
                break;
            case R.id.preparation_spinner:
                this.preparationChoice = position;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}
