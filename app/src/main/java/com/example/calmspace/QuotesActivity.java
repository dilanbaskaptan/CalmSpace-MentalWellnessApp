package com.example.calmspace;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class QuotesActivity extends AppCompatActivity {

    private TextView tvQuote, tvAuthor;
    private ProgressBar progressBar;
    private Button btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tvQuote = findViewById(R.id.tvQuote);
        tvAuthor = findViewById(R.id.tvAuthor);
        progressBar = findViewById(R.id.progressBar);
        btnRefresh = findViewById(R.id.btnRefresh);

        btnRefresh.setOnClickListener(v -> fetchQuote());
        fetchQuote();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchQuote() {
        progressBar.setVisibility(View.VISIBLE);
        tvQuote.setText("Loading...");
        tvAuthor.setText("");

        new Thread(() -> {
            try {
                URL url = new URL("https://zenquotes.io/api/random");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();

                JSONArray jsonArray = new JSONArray(result.toString());
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String quote = jsonObject.getString("q");
                String author = jsonObject.getString("a");

                runOnUiThread(() -> {
                    tvQuote.setText("\"" + quote + "\"");
                    tvAuthor.setText("— " + author);
                    progressBar.setVisibility(View.GONE);
                });

            } catch (Exception e) {
                runOnUiThread(() -> {
                    tvQuote.setText("Could not load quote. Check your connection.");
                    progressBar.setVisibility(View.GONE);
                });
            }
        }).start();
    }
}