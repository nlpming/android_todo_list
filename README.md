# Android Todo List App

A modern Android Todo List application built with Kotlin and Jetpack Compose, featuring user authentication, task management, and a beautiful Material Design 3 UI.

## Features

### Authentication
- User registration with validation
- Secure login with SHA-256 password hashing
- Local authentication using Room database
- Persistent session management with DataStore

### Todo Management
- Create, read, update, and delete todos
- Mark todos as complete/incomplete
- Filter todos by All, Active, or Completed
- Categorize tasks (Work, Personal, Health)
- Add due dates with date picker
- Edit existing todos with dialog interface
- Task statistics (today's tasks, completed count)

### UI/UX
- Material Design 3 components
- Purple gradient theme
- Dark theme support
- Smooth animations
- Empty state handling
- Error handling with snackbars
- Loading indicators

### Menu & Navigation
- Top menu with logout option
- Navigation between Login, Register, and Todo screens
- Auto-navigation based on session state

## Architecture

The app follows **Clean Architecture** with **MVVM** pattern:

```
app/
├── data/              # Data layer
│   ├── local/         # Room database, DAOs, entities
│   │   ├── dao/       # UserDao, TodoDao
│   │   ├── entity/    # UserEntity, TodoEntity
│   │   └── datastore/ # UserPreferences
│   └── repository/    # Repository implementations
├── domain/            # Domain layer
│   ├── model/         # Domain models (User, Todo, TodoCategory, TodoFilter)
│   ├── repository/    # Repository interfaces
│   └── usecase/       # Business logic use cases
├── presentation/      # Presentation layer
│   ├── login/         # Login screen, ViewModel, State
│   ├── register/      # Register screen, ViewModel, State
│   ├── todo/          # Todo screen, ViewModel, State, Components
│   ├── navigation/    # Navigation graph and routes
│   └── theme/         # Material Theme, Colors, Typography
└── di/                # Dependency Injection modules
```

## Tech Stack

### Core
- **Kotlin** - Programming language
- **Jetpack Compose** - Modern declarative UI
- **Material Design 3** - UI components and theming

### Architecture Components
- **ViewModel** - UI state management
- **StateFlow** - Reactive state handling
- **Navigation Compose** - Navigation between screens
- **Hilt** - Dependency injection

### Data Persistence
- **Room** - Local database
- **DataStore** - Key-value storage for preferences
- **Coroutines & Flow** - Asynchronous programming

### Build Configuration
- **Version Catalog** (libs.versions.toml) - Centralized dependency management
- **Kotlin DSL** - Gradle build scripts

## Project Structure

### Key Files

#### Configuration
- `gradle/libs.versions.toml` - Dependency versions
- `app/build.gradle.kts` - App-level build configuration
- `build.gradle.kts` - Root build configuration

#### Application Core
- `TodoApplication.kt` - Hilt application entry point
- `MainActivity.kt` - Compose activity with navigation

#### Data Layer (48 files total)
- `data/local/TodoDatabase.kt` - Room database
- `data/local/dao/UserDao.kt` - User database operations
- `data/local/dao/TodoDao.kt` - Todo database operations
- `data/local/entity/UserEntity.kt` - User table schema
- `data/local/entity/TodoEntity.kt` - Todo table schema
- `data/local/datastore/UserPreferences.kt` - Session management
- `data/repository/UserRepositoryImpl.kt` - User repository
- `data/repository/TodoRepositoryImpl.kt` - Todo repository

#### Domain Layer
- `domain/model/User.kt` - User domain model
- `domain/model/Todo.kt` - Todo domain model
- `domain/model/TodoCategory.kt` - Category enum (Work, Personal, Health)
- `domain/model/TodoFilter.kt` - Filter enum (All, Active, Completed)
- `domain/repository/UserRepository.kt` - User repository interface
- `domain/repository/TodoRepository.kt` - Todo repository interface
- `domain/usecase/` - 9 use cases for business logic

#### Dependency Injection
- `di/DatabaseModule.kt` - Provides database, DAOs, DataStore
- `di/RepositoryModule.kt` - Binds repository implementations

#### Presentation Layer
- `presentation/theme/` - Theme, colors, typography
- `presentation/navigation/` - NavGraph and Screen routes
- `presentation/login/` - Login UI, ViewModel, State
- `presentation/register/` - Register UI, ViewModel, State
- `presentation/todo/` - Todo UI, ViewModel, State
- `presentation/todo/components/` - Reusable Todo components

## Building the App

### Prerequisites
- Android Studio Hedgehog or newer
- JDK 11 or newer
- Android SDK 36 (min SDK 24)

### Build Instructions

1. Open the project in Android Studio
2. Sync Gradle files
3. Select a device or emulator (API 24+)
4. Run the app

### Command Line Build
```bash
./gradlew build
```

### Install Debug APK
```bash
./gradlew installDebug
```

## Usage Guide

### First Launch
1. App opens to Login screen
2. Click "Sign Up" to create an account
3. Enter username (min 3 chars, alphanumeric)
4. Enter display name
5. Create password (min 6 chars)
6. Click "Sign Up"

### Login
1. Enter registered username and password
2. Click "Login"
3. Session persists until logout

### Managing Todos
1. **Add Task**: Type in input field, select category, click +
2. **Complete Task**: Click checkbox
3. **Edit Task**: Click on task card to open edit dialog
4. **Delete Task**: Click × button
5. **Filter Tasks**: Use All/Active/Completed tabs
6. **Set Due Date**: Click task → Date icon → Select date

### Edit Todo Dialog
- Update title and description
- Change category
- Set or clear due date
- Save or cancel changes

### Logout
1. Click menu icon (⋮) in top right
2. Select "Logout"
3. Returns to Login screen

## Data Model

### User
```kotlin
data class User(
    val id: Long,
    val username: String,
    val displayName: String,
    val createdAt: Long
)
```

### Todo
```kotlin
data class Todo(
    val id: Long,
    val userId: Long,
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val category: TodoCategory,
    val dueDate: Long?,
    val createdAt: Long,
    val updatedAt: Long
)
```

### TodoCategory
- WORK (Purple #667EEA)
- PERSONAL (Purple #764BA2)
- HEALTH (Cyan #06B6D4)

## Security

- Passwords are hashed using SHA-256 before storage
- No plain-text passwords in database
- Session management via DataStore
- Local-only authentication (no backend)

## UI Components

### Reusable Components
- `TodoHeader` - Date display and task summary
- `TodoInput` - Input field with category selector
- `TodoTabs` - Filter tabs (All/Active/Completed)
- `TodoList` - LazyColumn with empty state
- `TodoItem` - Task card with checkbox and actions
- `CategoryTag` - Colored category chip
- `EditTodoDialog` - Modal dialog for editing tasks

### Theme
- Light and dark mode support
- Purple gradient backgrounds (#667EEA → #764BA2)
- Material Design 3 color scheme
- Custom typography and shapes

## Dependencies

See `gradle/libs.versions.toml` for versions:

- Compose BOM 2024.12.01
- Hilt 2.52
- Room 2.6.1
- Navigation Compose 2.8.5
- DataStore 1.1.1
- Coroutines 1.9.0
- Kotlin 2.1.0

## Future Enhancements

Potential features (out of current scope):
- Backend integration with REST API
- Cloud sync across devices
- Push notifications for due dates
- Recurring tasks
- Task priority levels
- Subtasks
- File attachments
- Search functionality
- Export/import data
- Analytics dashboard

## License

This is a sample project for educational purposes.

## Author

Generated by Claude Code
