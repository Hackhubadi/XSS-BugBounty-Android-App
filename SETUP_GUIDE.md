# 📱 Android Studio Setup Guide

## Complete Step-by-Step Instructions

### Prerequisites

#### 1. Install Java Development Kit (JDK)
```bash
# Download JDK 11 or higher
# Windows: https://www.oracle.com/java/technologies/downloads/
# Mac: brew install openjdk@11
# Linux: sudo apt install openjdk-11-jdk
```

#### 2. Install Android Studio
```
Download from: https://developer.android.com/studio
Version: Latest stable (Arctic Fox or newer)
```

---

## Setup Instructions

### Step 1: Clone the Repository
```bash
git clone https://github.com/Hackhubadi/XSS-BugBounty-Android-App.git
cd XSS-BugBounty-Android-App
```

### Step 2: Open in Android Studio
1. Launch Android Studio
2. Click **"Open an Existing Project"**
3. Navigate to cloned directory
4. Select the project folder
5. Click **"OK"**

### Step 3: Gradle Sync
```
Android Studio will automatically:
- Download required dependencies
- Sync Gradle files
- Index project files

Wait for "Gradle sync finished" message
```

### Step 4: Create Missing Layout Files

The repository contains Java code but you need to create XML layout files.

#### Create: `res/layout/activity_main.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp"
    android:gravity="center">

    <TextView
        android:id="@+id/tvAppVersion"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="XSS Bug Bounty Tester"
        android:textSize="24sp"
        android:textStyle="bold"
        android:layout_marginBottom="8dp"/>

    <TextView
        android:id="@+id/tvDisclaimer"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="⚠️ For authorized testing only"
        android:textColor="#FF5722"
        android:layout_marginBottom="32dp"/>

    <androidx.cardview.widget.CardView
        android:id="@+id/cardPayloadLibrary"
        android:layout_width="match_parent"
        android:layout_height="100dp"
        android:layout_margin="8dp"
        android:clickable="true"
        android:foreground="?android:attr/selectableItemBackground">
        
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="📚 Payload Library"
            android:textSize="18sp"
            android:layout_gravity="center"/>
    </androidx.cardview.widget.CardView>

    <androidx.cardview.widget.CardView
        android:id="@+id/cardTesting"
        android:layout_width="match_parent"
        android:layout_height="100dp"
        android:layout_margin="8dp"
        android:clickable="true"
        android:foreground="?android:attr/selectableItemBackground">
        
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="🔍 XSS Testing"
            android:textSize="18sp"
            android:layout_gravity="center"/>
    </androidx.cardview.widget.CardView>

    <androidx.cardview.widget.CardView
        android:id="@+id/cardReports"
        android:layout_width="match_parent"
        android:layout_height="100dp"
        android:layout_margin="8dp"
        android:clickable="true"
        android:foreground="?android:attr/selectableItemBackground">
        
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="📊 Reports"
            android:textSize="18sp"
            android:layout_gravity="center"/>
    </androidx.cardview.widget.CardView>

    <androidx.cardview.widget.CardView
        android:id="@+id/cardSettings"
        android:layout_width="match_parent"
        android:layout_height="100dp"
        android:layout_margin="8dp"
        android:clickable="true"
        android:foreground="?android:attr/selectableItemBackground">
        
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="⚙️ Settings"
            android:textSize="18sp"
            android:layout_gravity="center"/>
    </androidx.cardview.widget.CardView>

</LinearLayout>
```

#### Create: `res/layout/activity_payload_library.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <SearchView
        android:id="@+id/searchView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:queryHint="Search payloads..."
        android:iconifiedByDefault="false"/>

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerViewPayloads"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:padding="8dp"/>

</LinearLayout>
```

#### Create: `res/layout/activity_testing.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="16dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Target URL"
            android:textStyle="bold"/>

        <EditText
            android:id="@+id/etTargetUrl"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="https://example.com/search"
            android:inputType="textUri"/>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Parameter Name"
            android:textStyle="bold"
            android:layout_marginTop="16dp"/>

        <EditText
            android:id="@+id/etParameter"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="q"/>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="XSS Payload"
            android:textStyle="bold"
            android:layout_marginTop="16dp"/>

        <EditText
            android:id="@+id/etPayload"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="<script>alert('XSS')</script>"
            android:minLines="3"/>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="HTTP Method"
            android:textStyle="bold"
            android:layout_marginTop="16dp"/>

        <Spinner
            android:id="@+id/spinnerMethod"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"/>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Encoding"
            android:textStyle="bold"
            android:layout_marginTop="16dp"/>

        <Spinner
            android:id="@+id/spinnerEncoding"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"/>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:layout_marginTop="16dp">

            <Button
                android:id="@+id/btnTest"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:text="Test"
                android:layout_marginEnd="8dp"/>

            <Button
                android:id="@+id/btnClear"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:text="Clear"/>

        </LinearLayout>

        <Button
            android:id="@+id/btnUseFromLibrary"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Use Payload from Library"/>

        <ProgressBar
            android:id="@+id/progressBar"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:visibility="gone"
            android:layout_marginTop="16dp"/>

        <TextView
            android:id="@+id/tvStatus"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:textStyle="bold"
            android:textSize="16sp"
            android:layout_marginTop="16dp"/>

        <TextView
            android:id="@+id/tvResponseCode"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="8dp"/>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Response:"
            android:textStyle="bold"
            android:layout_marginTop="16dp"/>

        <ScrollView
            android:id="@+id/scrollViewResponse"
            android:layout_width="match_parent"
            android:layout_height="200dp"
            android:background="#F5F5F5"
            android:padding="8dp">

            <TextView
                android:id="@+id/tvResponse"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:textSize="12sp"
                android:fontFamily="monospace"/>

        </ScrollView>

    </LinearLayout>

</ScrollView>
```

#### Create: `res/layout/activity_report.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <TextView
        android:id="@+id/tvReportContent"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:textSize="16sp"/>

</LinearLayout>
```

#### Create: `res/layout/item_payload.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/cardViewPayload"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="8dp"
    app:cardCornerRadius="8dp"
    app:cardElevation="4dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="12dp">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal">

            <TextView
                android:id="@+id/tvPayloadName"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:text="Payload Name"
                android:textStyle="bold"
                android:textSize="16sp"/>

            <TextView
                android:id="@+id/tvPayloadCategory"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Category"
                android:textColor="#FFFFFF"
                android:paddingStart="8dp"
                android:paddingEnd="8dp"
                android:paddingTop="4dp"
                android:paddingBottom="4dp"
                android:textSize="12sp"/>

        </LinearLayout>

        <TextView
            android:id="@+id/tvPayloadContent"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Payload content"
            android:textSize="14sp"
            android:fontFamily="monospace"
            android:layout_marginTop="8dp"
            android:maxLines="3"
            android:ellipsize="end"/>

    </LinearLayout>

</androidx.cardview.widget.CardView>
```

### Step 5: SDK Configuration
```
1. Go to: Tools → SDK Manager
2. Install:
   - Android SDK Platform 33
   - Android SDK Build-Tools 33.0.0
   - Android Emulator
3. Click "Apply" and wait for download
```

### Step 6: Create Virtual Device (Optional)
```
1. Go to: Tools → Device Manager
2. Click "Create Device"
3. Select: Pixel 5 (or any device)
4. System Image: Android 13 (API 33)
5. Click "Finish"
```

### Step 7: Build the Project
```
1. Click: Build → Make Project
2. Wait for build to complete
3. Check for errors in "Build" tab
```

### Step 8: Run the App
```
Option A: On Emulator
1. Start emulator from Device Manager
2. Click Run button (▶️)
3. Select emulator

Option B: On Physical Device
1. Enable Developer Options on phone
2. Enable USB Debugging
3. Connect via USB
4. Click Run button (▶️)
5. Select your device
```

---

## Troubleshooting

### Issue: Gradle Sync Failed
```
Solution:
1. File → Invalidate Caches → Invalidate and Restart
2. Delete .gradle folder in project
3. Sync again
```

### Issue: SDK Not Found
```
Solution:
1. File → Project Structure
2. SDK Location → Set Android SDK path
3. Apply changes
```

### Issue: Build Errors
```
Solution:
1. Check build.gradle files match repository
2. Clean project: Build → Clean Project
3. Rebuild: Build → Rebuild Project
```

### Issue: App Crashes on Launch
```
Solution:
1. Check AndroidManifest.xml permissions
2. Verify all layout files exist
3. Check Logcat for error messages
```

---

## Project Structure Verification

After setup, your project should look like:
```
XSS-BugBounty-Android-App/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/bugbounty/xsstester/
│   │   │   │   ├── MainActivity.java ✓
│   │   │   │   ├── PayloadLibraryActivity.java ✓
│   │   │   │   ├── TestingActivity.java ✓
│   │   │   │   ├── ReportActivity.java ✓
│   │   │   │   ├── models/
│   │   │   │   │   ├── Payload.java ✓
│   │   │   │   │   └── TestResult.java ✓
│   │   │   │   ├── adapters/
│   │   │   │   │   └── PayloadAdapter.java ✓
│   │   │   │   └── utils/
│   │   │   │       ├── NetworkHelper.java ✓
│   │   │   │       └── PayloadEncoder.java ✓
│   │   │   ├── res/
│   │   │   │   └── layout/
│   │   │   │       ├── activity_main.xml ✓
│   │   │   │       ├── activity_payload_library.xml ✓
│   │   │   │       ├── activity_testing.xml ✓
│   │   │   │       ├── activity_report.xml ✓
│   │   │   │       └── item_payload.xml ✓
│   │   │   └── AndroidManifest.xml ✓
│   │   └── build.gradle ✓
│   └── build.gradle ✓
├── README.md ✓
├── LICENSE ✓
└── BUG_BOUNTY_GUIDE.md ✓
```

---

## Next Steps

1. ✅ Build successful
2. ✅ App runs on device/emulator
3. ✅ Read BUG_BOUNTY_GUIDE.md
4. ✅ Review PAYLOAD_CHEATSHEET.md
5. ✅ Start ethical testing!

---

## Support

**Issues?** Open an issue on GitHub:
https://github.com/Hackhubadi/XSS-BugBounty-Android-App/issues

**Questions?** Contact:
- Email: adi246965@gmail.com
- GitHub: @Hackhubadi

---

**Happy (Ethical) Hacking! 🛡️**
