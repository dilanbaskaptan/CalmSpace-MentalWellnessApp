# CalmSpace 🌙

**A Mental Wellness Companion App for Individuals Experiencing Anxiety and Stress**

CMPE2004 — Advanced Programming Term Project

## About

CalmSpace is an Android mobile application designed to support individuals experiencing anxiety, stress, and emotional overload. The app provides practical tools for emotional regulation, including guided breathing exercises, motivational content, calming sounds, and light wellness routines — all in one simple, accessible interface.

## Features

- **Splash Screen** — Animated opening screen with the CalmSpace logo, automatically transitions to the Home screen after 2.5 seconds
- **Home** — Personalized welcome message (saved via SharedPreferences), navigation hub to all features, and a "Send Me Motivation" notification button
- **Motivational Quotes** — Retrieves real-time motivational quotes from the ZenQuotes API via HTTP requests and JSON parsing
- **Breathing Exercise** — Guided 4-second inhale / 4-second hold / 4-second exhale cycle with an animated breathing circle
- **Wellness Exercises** — 5 guided relaxation movements (Arm Circles, Belly Rotation, Side Stretch, Meditation Pose, Forward Bend), each with a 20-second timer and completion beep
- **Calming Sounds** — Loopable nature sounds (Rain, Ocean Waves, Forest Birds) using MediaPlayer

## Built With

- **Language:** Java
- **IDE:** Android Studio
- **Design:** Material Design (CardView, Toolbar, custom themes & colors)
- **API:** ZenQuotes API — HTTP requests via HttpURLConnection
- **Android Components:** Activities, Explicit Intents, NotificationManager, MediaPlayer, CountDownTimer, ObjectAnimator, ToneGenerator, SharedPreferences

## Project Structure

```
com.example.calmspace/
├── SplashActivity.java          — Opening screen with logo & auto-navigation
├── MainActivity.java            — Home screen, navigation, notifications
├── QuotesActivity.java          — ZenQuotes API integration
├── BreathingExerciseActivity.java — Guided breathing animation & timer
├── ExerciseActivity.java        — Wellness exercises with timers
├── SoundsActivity.java          — Calming nature sounds (MediaPlayer)
├── HealthTip.java               — OOP model class (encapsulation)
├── Trackable.java               — Interface: saveEntry(), deleteEntry()
└── res/
    ├── layout/    — XML layouts for each activity
    ├── drawable/  — Background images, logo, icons
    └── values/    — Colors, themes, strings
```

## Object-Oriented Design

- **Trackable** — an interface defining `saveEntry()` and `deleteEntry()`
- **HealthTip** — implements `Trackable`, demonstrating encapsulation (private fields with getters/setters) and interface usage
- **Exception Handling** — try-catch blocks used throughout (MediaPlayer, CountDownTimer, network calls) to prevent crashes
- **Explicit Intents** — used for navigation between all 6 activities

## Permissions

- `INTERNET` — for fetching quotes from the ZenQuotes API
- `POST_NOTIFICATIONS` — runtime permission for Android 13+ to send motivational notifications

## Design

- **Color Palette:** Soft purple (#534AB7) and blue (#378ADD) tones for a calming, accessible aesthetic
- **Custom Logo:** Moon and stars design, used as the app launcher icon
- Unique background imagery for each screen, consistent button/toolbar/card styling

## Team

- **Dilan Başkaptan** — Home Screen, Calming Sounds, Notifications, Breathing Exercise, Wellness Exercises, visual design & OOP architecture
- **Furkan Arslan** — Splash Screen, Motivational Quotes & API integration

## Getting Started

1. Clone the repository
2. Open the project in Android Studio
3. Let Gradle sync dependencies
4. Run on an emulator or physical Android device (API 24+)

## Future Improvements

- Mood tracking with history view
- Customizable notification scheduling
- Dark mode support
- Additional guided exercises and sounds
