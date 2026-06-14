package com.example.calmspace;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "calmspace_channel";
    private static final int PERMISSION_CODE = 100;
    private EditText etName;
    private TextView tvSubtitle;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();

        etName = findViewById(R.id.etName);
        tvSubtitle = findViewById(R.id.tvSubtitle);
        prefs = getSharedPreferences("CalmSpacePrefs", MODE_PRIVATE);

        String savedName = prefs.getString("username", "");
        if (!savedName.isEmpty()) {
            etName.setVisibility(View.GONE);
            tvSubtitle.setText("Welcome back, " + savedName + "! 💙");
        }

        etName.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String name = etName.getText().toString().trim();
                if (!name.isEmpty()) {
                    prefs.edit().putString("username", name).apply();
                    tvSubtitle.setText("Welcome, " + name + "! 💙");
                    etName.setVisibility(View.GONE);
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etName.getWindowToken(), 0);
                }
                return true;
            }
            return false;
        });

        Button btnQuotes = findViewById(R.id.btnQuotes);
        Button btnBreathing = findViewById(R.id.btnBreathing);
        Button btnExercise = findViewById(R.id.btnExercise);
        Button btnSounds = findViewById(R.id.btnSounds);
        Button btnNotification = findViewById(R.id.btnNotification);

        btnQuotes.setOnClickListener(v -> startActivity(new Intent(this, QuotesActivity.class)));
        btnBreathing.setOnClickListener(v -> startActivity(new Intent(this, BreathingExerciseActivity.class)));
        btnExercise.setOnClickListener(v -> startActivity(new Intent(this, ExerciseActivity.class)));
        btnSounds.setOnClickListener(v -> startActivity(new Intent(this, SoundsActivity.class)));
        btnNotification.setOnClickListener(v -> sendMotivationNotification());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Home");
        menu.add(0, 2, 1, "About");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 2) {
            Toast.makeText(this,
                    "CalmSpace - Your mental wellness companion",
                    Toast.LENGTH_LONG).show();
        }
        return true;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "CalmSpace", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Motivational notifications");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private void sendMotivationNotification() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.POST_NOTIFICATIONS)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.POST_NOTIFICATIONS},
                            PERMISSION_CODE);
                    return;
                }
            }

            String[] messages = {
                    "Take a deep breath. You are doing great!",
                    "You are stronger than you think. 💙",
                    "One step at a time. You've got this!",
                    "Remember to take care of yourself today.",
                    "Breathe in peace, breathe out stress.",
                    "You are safe. Everything will be okay."
            };

            String randomMessage = messages[(int) (Math.random() * messages.length)];

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("CalmSpace 💙")
                    .setContentText(randomMessage)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true);

            NotificationManagerCompat manager = NotificationManagerCompat.from(this);
            manager.notify(1, builder.build());

            Toast.makeText(this, "Motivation sent!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}