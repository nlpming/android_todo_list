# Quick Start Guide

## Getting Started in 5 Minutes

### Step 1: Open in Android Studio
1. Launch Android Studio
2. Click "Open" (not "New Project")
3. Navigate to `/Users/mac/AndroidStudioProjects/MyApplication`
4. Click "OK"

### Step 2: Wait for Gradle Sync
- Android Studio will automatically sync Gradle
- This may take 2-5 minutes on first run
- Watch the bottom status bar for progress
- Wait for "Gradle sync finished" message

### Step 3: Verify Setup
Check that:
- No red errors in the code
- Build tools downloaded successfully
- Dependencies resolved

### Step 4: Run the App
1. Click the green "Run" button (or press Shift+F10)
2. Select a device:
   - **Physical device**: Enable USB debugging and connect
   - **Emulator**: Create one with API 24+ if needed
3. Click "OK"

### Step 5: Test the App
1. **Register a new account:**
   - Click "Sign Up"
   - Username: `testuser`
   - Display Name: `Test User`
   - Password: `test123`
   - Confirm Password: `test123`
   - Click "Sign Up"

2. **Login:**
   - Username: `testuser`
   - Password: `test123`
   - Click "Login"

3. **Add a todo:**
   - Type "Buy groceries" in the input field
   - Select category (try "Personal")
   - Click the + button

4. **Manage todos:**
   - Click checkbox to mark complete
   - Click the task to edit it
   - Click × to delete
   - Use tabs to filter (All/Active/Completed)

5. **Test logout:**
   - Click menu icon (⋮) in top right
   - Click "Logout"
   - Should return to login screen

## What You Should See

### Login Screen
- Purple gradient background
- "Welcome Back" title
- Username and password fields
- Login and Sign Up buttons

### Register Screen
- Purple gradient background
- "Create Account" title
- Username, display name, password fields
- Sign Up button

### Todo Screen
- Purple gradient header with date
- "My Tasks" title
- Task statistics ("X tasks today · Y completed")
- Input field with category selector
- All/Active/Completed tabs
- List of todos with checkboxes
- Menu icon with logout option

## Expected Behavior

### ✅ Working Features
- User registration with validation
- Secure login
- Session persistence (stays logged in after restart)
- Add todos
- Edit todos (click on task)
- Complete/uncomplete todos
- Delete todos
- Filter todos
- Category selection
- Due date picker (in edit dialog)
- Logout functionality
- Dark theme (follows system setting)

### 📱 Testing Checklist
- [ ] Can register new user
- [ ] Can login with credentials
- [ ] Session persists (close and reopen app)
- [ ] Can add todos
- [ ] Can mark todos complete
- [ ] Can edit todos
- [ ] Can delete todos
- [ ] Filters work (All/Active/Completed)
- [ ] Categories display with colors
- [ ] Can set due dates
- [ ] Can logout
- [ ] Dark theme works

## Common First-Run Issues

### Issue: Gradle sync fails
**Solution:** Wait for it to complete. First sync downloads ~200MB of dependencies.

### Issue: No emulator available
**Solution:**
1. Tools → Device Manager
2. Create Device
3. Select Pixel 6 or similar
4. Download system image (API 34 recommended)
5. Finish and start emulator

### Issue: "SDK not found"
**Solution:**
1. File → Project Structure
2. SDK Location
3. Android SDK Location should be set (usually `/Users/mac/Library/Android/sdk`)
4. Install SDK 36 if missing

### Issue: App crashes on launch
**Solution:**
1. Check Logcat (View → Tool Windows → Logcat)
2. Look for exception messages
3. Most common: Hilt setup issue - do Clean & Rebuild

## Project Structure Quick Reference

```
MyApplication/
├── app/
│   ├── build.gradle.kts          ← App dependencies
│   └── src/main/
│       ├── AndroidManifest.xml   ← App manifest
│       └── java/com/example/myapplication/
│           ├── TodoApplication.kt      ← Hilt app
│           ├── MainActivity.kt         ← Entry point
│           ├── data/                   ← Data layer
│           │   ├── local/             ← Room DB
│           │   └── repository/        ← Repositories
│           ├── domain/                 ← Business logic
│           │   ├── model/             ← Models
│           │   ├── repository/        ← Interfaces
│           │   └── usecase/           ← Use cases
│           ├── presentation/           ← UI layer
│           │   ├── login/             ← Login screen
│           │   ├── register/          ← Register screen
│           │   ├── todo/              ← Todo screen
│           │   ├── theme/             ← App theme
│           │   └── navigation/        ← Navigation
│           └── di/                     ← DI modules
├── gradle/
│   └── libs.versions.toml        ← Dependency versions
├── build.gradle.kts              ← Root config
├── README.md                     ← Full documentation
├── IMPLEMENTATION_SUMMARY.md     ← What was built
├── TROUBLESHOOTING.md           ← Fix common issues
└── QUICKSTART.md                ← This file
```

## Key Files to Understand

### Entry Points
- `TodoApplication.kt` - App initialization, Hilt setup
- `MainActivity.kt` - Compose UI setup, navigation
- `NavGraph.kt` - Navigation flow between screens

### Authentication
- `LoginScreen.kt` - Login UI
- `RegisterScreen.kt` - Registration UI
- `UserRepository.kt` - Auth operations

### Todo Management
- `TodoScreen.kt` - Main todo UI
- `TodoViewModel.kt` - Todo business logic
- `TodoRepository.kt` - Todo data operations

### Database
- `TodoDatabase.kt` - Room database
- `UserEntity.kt` & `TodoEntity.kt` - Database tables
- `UserDao.kt` & `TodoDao.kt` - Database queries

## Development Tips

### Making Changes
1. Modify code
2. Click Build → Make Project (Cmd+F9)
3. Run app again

### Viewing Logs
- View → Tool Windows → Logcat
- Filter by "MyApplication" or "TodoViewModel"

### Testing Dark Mode
- Swipe down from top of screen
- Toggle dark mode
- App should update automatically

### Debugging
- Set breakpoints by clicking left margin
- Click Debug button instead of Run
- Step through code with debugger controls

## Next Steps

### For Learning
1. Read through `README.md` for full documentation
2. Explore the code structure
3. Try modifying UI colors in `Color.kt`
4. Add a new category in `TodoCategory.kt`

### For Development
1. Add unit tests (see Testing section in README)
2. Customize the theme
3. Add new features (see Future Enhancements in README)
4. Deploy to device

## Support

- **Full docs:** See README.md
- **Build issues:** See TROUBLESHOOTING.md
- **Implementation details:** See IMPLEMENTATION_SUMMARY.md

## Success!

If you can register, login, add todos, and manage them - congratulations! The app is working perfectly. 🎉

The implementation follows Android best practices:
- ✅ Clean Architecture
- ✅ MVVM Pattern
- ✅ Jetpack Compose
- ✅ Material Design 3
- ✅ Dependency Injection with Hilt
- ✅ Room Database
- ✅ Coroutines & Flow

You now have a fully functional, production-ready todo app structure!
