# Troubleshooting Guide

## Common Build Issues and Solutions

### 1. Gradle Sync Failures

#### Issue: "Could not resolve dependencies"
**Solution:**
```bash
# Clear Gradle cache
rm -rf ~/.gradle/caches/
# In Android Studio: File → Invalidate Caches → Invalidate and Restart
```

#### Issue: "Failed to find target with hash string 'android-36'"
**Solution:**
- Open Android Studio → Tools → SDK Manager
- Install Android SDK 36 (API Level 36)
- Install Android SDK Build-Tools 36.0.0

### 2. Kotlin Compilation Errors

#### Issue: "Unresolved reference" errors
**Solution:**
1. Sync Gradle: File → Sync Project with Gradle Files
2. Rebuild: Build → Rebuild Project
3. If still failing, check that all dependencies are properly declared in libs.versions.toml

#### Issue: Compose compiler version mismatch
**Solution:**
- Ensure Kotlin version and Compose compiler version are compatible
- Current setup: Kotlin 2.1.0 with Compose Compiler 1.5.15

### 3. Hilt/KAPT Issues

#### Issue: "Hilt components not generated"
**Solution:**
1. Ensure `@HiltAndroidApp` is on TodoApplication
2. Ensure `@AndroidEntryPoint` is on MainActivity
3. Clean and rebuild:
```bash
./gradlew clean
./gradlew build
```

#### Issue: KAPT taking too long
**Solution:**
Add to `gradle.properties`:
```properties
kapt.use.worker.api=true
kapt.incremental.apt=true
```

### 4. Room Database Issues

#### Issue: "Cannot find implementation for TodoDatabase"
**Solution:**
1. Ensure Room plugin is applied in build.gradle.kts
2. Clean and rebuild project
3. Check that @Database annotation is correct

#### Issue: "Schema export directory not found"
**Solution:**
- The directory will be created automatically during build
- If issues persist, create manually: `mkdir -p app/schemas`

### 5. Runtime Crashes

#### Issue: "No implementation found for UserRepository"
**Solution:**
- Verify `@Binds` in RepositoryModule
- Ensure `@Inject constructor` on repository implementations
- Check Hilt setup in TodoApplication

#### Issue: "Unable to create database"
**Solution:**
- Check app has storage permissions (should be automatic)
- Try uninstalling and reinstalling the app
- Clear app data: Settings → Apps → MyApplication → Clear Data

#### Issue: "DataStore initialization error"
**Solution:**
- Ensure UserPreferences is provided in DatabaseModule
- Check Context is properly injected

### 6. UI Issues

#### Issue: "Compose preview not working"
**Solution:**
1. Ensure preview dependencies are in build.gradle.kts
2. Invalidate caches and restart Android Studio
3. Check that @Preview annotations are present (if you add them)

#### Issue: "Navigation not working"
**Solution:**
- Verify NavGraph is properly set up in MainActivity
- Check Screen routes match in navigation calls
- Ensure navController is from rememberNavController()

### 7. Java/JDK Issues

#### Issue: "No Java runtime found"
**Solution:**
1. Install JDK 11 or newer
2. Set JAVA_HOME:
```bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-11.jdk/Contents/Home
```
3. Or configure in Android Studio: File → Project Structure → SDK Location

#### Issue: "Unsupported class file major version"
**Solution:**
- Ensure JDK version matches project requirements (JDK 11)
- Update build.gradle.kts if needed

### 8. Build Performance

#### Issue: Slow build times
**Solution:**
Add to `gradle.properties`:
```properties
org.gradle.jvmargs=-Xmx2048m -XX:MaxMetaspaceSize=512m
org.gradle.parallel=true
org.gradle.caching=true
kotlin.incremental=true
kotlin.daemon.jvmargs=-Xmx2g
```

### 9. Version Conflicts

#### Issue: "Duplicate class found"
**Solution:**
- Check for conflicting dependency versions
- Use BOM (Bill of Materials) for Compose dependencies
- Exclude transitive dependencies if needed

### 10. Emulator Issues

#### Issue: App not installing on emulator
**Solution:**
1. Ensure emulator API level is >= 24
2. Wipe emulator data and restart
3. Try a different emulator configuration

#### Issue: "INSTALL_FAILED_INSUFFICIENT_STORAGE"
**Solution:**
- Wipe emulator data
- Create new emulator with more storage
- Use a physical device

## Verification Steps

### After Successful Build:

1. **Check APK generated:**
```bash
ls -lh app/build/outputs/apk/debug/app-debug.apk
```

2. **Install on device:**
```bash
./gradlew installDebug
```

3. **View logs:**
```bash
adb logcat | grep MyApplication
```

## Manual Fixes

### If Build Still Fails:

1. **Clean everything:**
```bash
./gradlew clean
rm -rf .gradle/
rm -rf app/build/
rm -rf build/
```

2. **Reimport project:**
- Close Android Studio
- Delete `.idea/` folder
- Reopen project in Android Studio

3. **Check file integrity:**
```bash
find app/src/main/java -name "*.kt" | xargs grep -l "package com.example.myapplication"
```

## Getting Help

### Where to look:

1. **Build output:** View → Tool Windows → Build
2. **Logcat:** View → Tool Windows → Logcat
3. **Gradle console:** View → Tool Windows → Gradle

### Common error patterns:

- "Unresolved reference" → Missing import or Gradle sync needed
- "Type mismatch" → Check function signatures
- "Cannot access" → Check visibility modifiers
- "No such file" → Check file paths and package names

## Environment Check

### Verify your setup:

```bash
# Check Java version
java -version  # Should be 11+

# Check Kotlin version
./gradlew -version

# Check Android SDK
ls $ANDROID_HOME/platforms/  # Should include android-36
```

### Required tools:
- ✅ Android Studio Hedgehog (2023.1.1) or newer
- ✅ JDK 11 or newer
- ✅ Android SDK 36
- ✅ Gradle 8.0+ (managed by wrapper)

## Quick Fixes Checklist

- [ ] Gradle synced successfully
- [ ] JDK 11+ configured
- [ ] Android SDK 36 installed
- [ ] All dependencies downloaded
- [ ] No compilation errors
- [ ] Hilt annotation processing completed
- [ ] Room schema generated
- [ ] APK built successfully

## Still Having Issues?

If you've tried all the above and still have problems:

1. Check the exact error message in Build Output
2. Search for the error on Stack Overflow
3. Check if it's a known issue with the dependency versions
4. Try building a fresh Android Studio project to verify your setup
5. Consider updating Android Studio to the latest stable version

## Success Indicators

When everything is working correctly:

- ✅ Gradle sync completes without errors
- ✅ Build succeeds (may take 2-5 minutes first time)
- ✅ APK is generated in `app/build/outputs/apk/debug/`
- ✅ App installs on device/emulator
- ✅ Login screen appears on launch
- ✅ Can register, login, and manage todos

## Performance Notes

**First build:** May take 5-10 minutes (downloads dependencies)
**Subsequent builds:** Should take 30-60 seconds
**Clean builds:** Take 2-3 minutes

If builds are consistently slow, check the Build Performance section above.
