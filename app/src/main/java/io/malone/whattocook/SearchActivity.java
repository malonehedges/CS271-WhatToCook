package io.malone.whattocook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        this.initializeSpinners();
        this.initializeButton();
    }

    void performSearch() {
        // Get the appropriate results based on the selected options
        // TODO

        // Start the ResultActivity, passing the results
        Intent resultIntent = new Intent(this, ResultActivity.class);
        // ... TODO

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

    private void initializeSpinners() {
        Spinner dietSpinner = findViewById(R.id.diet_spinner);
        Spinner servingSpinner = findViewById(R.id.serving_spinner);
        Spinner preparationSpinner = findViewById(R.id.preparation_spinner);

//        Spinner dropdown = findViewById(R.id.diet_spinner);
//        String[] items = new String[] {"1", "2", "three"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//        dropdown.setAdapter(adapter);
    }
}
