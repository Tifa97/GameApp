## GameApp

GameApp is an Android application that allows users to explore and discover various games. 
It follows the MVVM architecture pattern for structured and maintainable code.
MVVM was chosen for GameApp due to its separation of UI from business logic which improves code readability
maintainability and testability.

### Architecture

The app follows the MVVM architecture pattern for structured and maintainable code.
MVVM was chosen for GameApp due to its separation of UI from business logic which improves code readability
maintainability and testability. MVVM was preferred over MVI for
it's simpler design, which reduces complexity and overhead in managing application state and user interactions,
leading to a more straightforward development process.

### UI

App contains 3 screens. GenreSelectionScreen, HomeScreen and GameDetailsScreen:

1.  **GenreSelectionScreen**: Displays genres fetched from the server for user to select and saves them to database when clicking Save button. It serves as an onboarding screen, but can also be accessed by clicking settings icon on HomeScreen.

2.  **HomeScreen**: Fetches and displays games for the selected genre. Genre can be chosen in the top bar. Also contains settings icon that leads to GenreSelectionScreen

3.  **GameDetailsScreen**: Fetches and displays details for the selected game. Contains game image on top with details contained in a scrollable column under the image.

### Error Handling

1.  **Oops.. We can't fetch** - getting an error like this on the screen means that there was something wrong while fetching data (no connection, server issues...)

2.  **I couldn\'t find the proper endpoint** - this is a hardcoded error since I couldn't find an appropriate endpoint for games for RPG genre and genres that contain multiple words (e.g. Massive Multiplayer) in the documentation.

3.  **You haven't selected any genre.** - technically not an error, more a message on the screen for the user to select genres if not selected.

### Packages

1.  **db**: Contains the ROOM database setup, entities, and DAO for interacting with the database.

2.  **di**: Contains the dependency injection setup inside Modules.kt file with the Koin module for providing dependencies throughout the app.

3.  **navigation**: Manages navigation within the app, including the NavHost setup and screen routes.

4.  **remote**: Handles remote data fetching using Retrofit, including the definition of API endpoints and response data classes.

5.  **repository**: Contains data store preferences and repositories for abstracting access to remote and database data.

6.  **ui**: Contains Jetpack Compose theming and UI components, including various composables for different screens within the app.

7.  **util**: Contains utility classes and functions used throughout the app.

8.  **viewmodel**: Contains ViewModel classes for each screen in the app, responsible for managing UI-related data and communication with the repository layer.

### Dependencies

The app uses various dependencies, including:

-   Jetpack Compose for building the UI.
-   Datastore for persisting data about onboarding state.
-   ROOM for local database management.
-   Retrofit for making network requests.
-   Koin for dependency injection. Koin was chosen over Dagger Hilt for its simplicity and ease of use, enabling efficient dependency injection throughout the app without the need for complex setup and annotations
-   Android Navigation Component for managing navigation within the app.
-   Splashscreen for handling splash screen functionality.
-   Coil for handling load and display of images
-   Firebase (only imported into project and connected to firebase console).
