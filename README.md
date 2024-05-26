## AutolabsChallenge
An app that displays news articles fetched from newsapi. It includes features like speech recognition to reload the news list and supports 
sorting news articles by their publication date. The app is built using Jetpack Compose for the UI, Coroutines for async functions, 
Hilt for dependency injection, and integrates Google's SpeechRecognizer.

## Features
Display news articles fetched from NewsAPI.
Sort news articles by publication date.
Reload news articles using voice commands.
Modern UI built with Jetpack Compose.
Dependency injection with Hilt.

## Getting Started
### Prerequisites
- Android Studio
- A NewsAPI key. Sign up at https://newsapi.org/ to get your API key.
- An internet connection to fetch news articles.
### Installation
- In Android Studio open the project
- Run the debug variant on a device or emulator
- Add your NewsAPI key to a variable named NEWS_API_KEY, to the local.properties file
- Build and run the project on an emulator or physical device.

## Usage
### Main Screens
- News List Screen: Displays a list of news articles. Users can sort articles and reload the list using voice commands.
- News Detail Screen: Shows detailed information about a selected news article.
### Voice Commands
- Long press the microphone button to start speech recognition.
- Say "Reload" to refresh the news list.

## Architecture
The app follows the MVVM (Model-View-ViewModel) architecture pattern and is built with the following components:

- **ViewModel**: Manages UI-related data and handles the logic for fetching and sorting news articles.
- **Repository**: Fetches data from the NewsAPI and handles data operations.
- **UI**: Built with Jetpack Compose to provide a modern and responsive user interface.
- **Hilt**: Manages dependency injection to provide a clean and maintainable codebase.

## Dependencies
- Jetpack Compose
- Coroutines
- Hilt
- Retrofit for network calls
- Coil for image loading
- Google's SpeechRecognizer for speech recognition
- JUnit 4
- Mockito
