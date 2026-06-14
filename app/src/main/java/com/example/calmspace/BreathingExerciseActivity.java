package com.example.calmspace;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class BreathingExerciseActivity extends AppCompatActivity {

    private TextView tvInstruction, tvTimer;
    private View breathingCircle;
    private Button btnStart, btnStop;
    private CountDownTimer countDownTimer;
    private int phase = 0;
    private boolean isRunning = false;

    private final String[] phases = {"Inhale", "Hold", "Exhale"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breathing);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tvInstruction = findViewById(R.id.tvInstruction);
        tvTimer = findViewById(R.id.tvTimer);
        breathingCircle = findViewById(R.id.breathingCircle);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);

        btnStart.setOnClickListener(v -> startBreathing());
        btnStop.setOnClickListener(v -> stopBreathing());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startBreathing() {
        if (isRunning) return;
        isRunning = true;
        phase = 0;
        nextPhase();
    }

    private void nextPhase() {
        if (!isRunning) return;

        tvInstruction.setText(phases[phase]);
        animateCircle(phase);

        try {
            countDownTimer = new CountDownTimer(4000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tvTimer.setText(String.valueOf(millisUntilFinished / 1000 + 1));
                }

                @Override
                public void onFinish() {
                    phase = (phase + 1) % 3;
                    nextPhase();
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void animateCircle(int phase) {
        float scale = (phase == 0) ? 1.3f : (phase == 2) ? 0.8f : 1.0f;
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(breathingCircle, "scaleX", scale);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(breathingCircle, "scaleY", scale);
        scaleX.setDuration(1000);
        scaleY.setDuration(1000);
        scaleX.start();
        scaleY.start();
    }

    private void stopBreathing() {
        isRunning = false;
        if (countDownTimer != null) countDownTimer.cancel();
        tvInstruction.setText("Press Start to Begin");
        tvTimer.setText("4");
        breathingCircle.setScaleX(1f);
        breathingCircle.setScaleY(1f);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) countDownTimer.cancel();
    }
}