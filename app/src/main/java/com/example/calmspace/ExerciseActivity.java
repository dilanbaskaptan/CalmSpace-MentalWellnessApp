package com.example.calmspace;

import android.media.ToneGenerator;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ExerciseActivity extends AppCompatActivity {

    private ImageView ivExercise;
    private TextView tvExerciseName, tvDescription, tvTimer, tvProgress;
    private Button btnStart, btnPrev, btnNext;
    private CountDownTimer countDownTimer;
    private int currentIndex = 0;
    private boolean isRunning = false;

    private final String[] names = {
            "Arm Circles", "Belly Rotation", "Side Stretch", "Meditation Pose", "Forward Bend"
    };

    private final String[] descriptions = {
            "Stand and rotate your arms in circles",
            "Stand and rotate your belly slowly",
            "Stretch to the side, arms up",
            "Sit cross-legged, breathe deeply",
            "Sit and stretch forward, hold the position"
    };

    private final int[] images = {
            R.drawable.pose_arm,
            R.drawable.pose_belly,
            R.drawable.pose_move,
            R.drawable.pose_meditation,
            R.drawable.pose_stretch
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ivExercise = findViewById(R.id.ivExercise);
        tvExerciseName = findViewById(R.id.tvExerciseName);
        tvDescription = findViewById(R.id.tvDescription);
        tvTimer = findViewById(R.id.tvTimer);
        tvProgress = findViewById(R.id.tvProgress);
        btnStart = findViewById(R.id.btnStart);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);

        updateExercise();

        btnStart.setOnClickListener(v -> {
            if (isRunning) {
                stopTimer();
            } else {
                startTimer();
            }
        });

        btnPrev.setOnClickListener(v -> {
            stopTimer();
            currentIndex = (currentIndex - 1 + names.length) % names.length;
            updateExercise();
        });

        btnNext.setOnClickListener(v -> {
            stopTimer();
            currentIndex = (currentIndex + 1) % names.length;
            updateExercise();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateExercise() {
        tvExerciseName.setText(names[currentIndex]);
        tvDescription.setText(descriptions[currentIndex]);
        ivExercise.setImageResource(images[currentIndex]);
        tvTimer.setText("20");
        tvProgress.setText((currentIndex + 1) + " / " + names.length);
        btnStart.setText("Start");
    }

    private void playBeep() {
        try {
            ToneGenerator toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
            toneGen.startTone(ToneGenerator.TONE_PROP_BEEP, 500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startTimer() {
        isRunning = true;
        btnStart.setText("Stop");
        try {
            countDownTimer = new CountDownTimer(20000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tvTimer.setText(String.valueOf(millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    tvTimer.setText("Done!");
                    btnStart.setText("Start");
                    isRunning = false;
                    playBeep();
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopTimer() {
        if (countDownTimer != null) countDownTimer.cancel();
        isRunning = false;
        btnStart.setText("Start");
        tvTimer.setText("20");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) countDownTimer.cancel();
    }
}