SEED Pomodoro
SEED Pomodoro is an Android application designed to help users manage their study time using the Pomodoro technique.
The app allows users to start and track focus sessions, customize timer durations, and view basic study statistics.

#Features

- Pomodoro timer with start, pause, and reset functionality
- Customizable Pomodoro and break durations via Settings screen
- Automatic saving of completed study sessions
- Statistics screen showing:
  - Total number of completed sessions
  - Total study time in minutes
  - List of individual study sessions with date and duration
- Simple and clean user interface built with Jetpack Compose
- Bottom navigation for Timer, Statistics, and Settings screens

#Architecture
The application follows the MVVM (Model–View–ViewModel) architecture pattern.
- UI layer: Jetpack Compose
- ViewModel layer: Android ViewModel with StateFlow
- Data layer:
  - Room database for storing study sessions
  - DataStore for saving user settings (Pomodoro and break durations)

#Technologies Used
- Kotlin
- Android Jetpack Compose
- Material 3
- ViewModel and StateFlow
- Room Database
- DataStore Preferences
- Navigation Compose

#Screens
- Welcome Screen: Entry screen with app logo and start button
- Timer Screen: Pomodoro timer with circular progress indicator
- Statistics Screen: Displays study statistics and session history
- Settings Screen: Allows users to change Pomodoro and break durations

#Installation

1. Clone the repository:  git clone https://github.com/nisatatli/SEEDPomodoro.git
2. Open the project in Android Studio.
3. Sync Gradle files.
4. Run the app on an Android emulator or physical device.

#Notes
- The app currently targets Android API 33+.
- No external sensors are used.
- Internet connection is not required for core functionality.


[Developed as a medium-level Android project for educational purposes.]
