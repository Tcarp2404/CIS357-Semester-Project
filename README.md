 - Overview
This tutorial demonstrates how to implement on-device storage in Adroid using a app called MindLift. The app allows users to store thoughts categorized by emotions with the optional addition of taking a picture that can be attached to the thougt. The data is persistent and remains on the app locally so its avaible through different sessions. 

The primary focus of this tutorial will be on local data persistance or on-device storage. More specifically some things that will be talked about inlcude saving structured data using JSON files, storing images inteernally, and making sure the data loads when the app starts up. Unlike cloud based functions like Firebase this approach keeps all of the data offline and stored locally on the device while staying private and secure.

 - Getting Started
To follow this tutorial you will need:
Android Studio (latest version recommended)
Kotlin support enabled
Android SDK (API 24+ recommended)

Dependencies: Add the following to your build.gradle (Module: app) implementation "com.google.code.gson:gson:2.10.1" - This library is used to convert kotlin objects to JSON and back.

Device Requirements: Android Emulator or physical android device, camera permission enabled (fro image capturing)

 - Step by Step Implementation
Step 1. Begin by defining a "Thought" data class

data class Thought(
    val text: String,
    val imagePath: String? = null
)

Each thought contains: text value, and an optional file path to an image stored locally on the device.

Step 2. Store data in memory by using a viewmodel to manage UI state.

private val _thoughts = MutableStateFlow(
    mutableMapOf<Emotion, MutableList<Thought>>()
)

This will allow reactive UI updates, and seperation of UI and logic.

Step 3. Save data to internal storage by creating a helper function to handle saving.

val file = File(context.filesDir, "thoughts.json")
file.writeText(json)

The key concept is filesDir being a private app storage, data is not accessible by other apps, and data persists after the app closes.

Step 4. Convert data to JSON using Gson.

val json = Gson().toJson(data)
Gson().fromJson(json, type)

We use JSON because it is lightweight and easy to store stuctured data.

Step 5. Load data on app start.

LaunchedEffect(Unit) {
    viewModel.loadData(context)
}

This ensures the data is restored every time the app launches.

Step 6. Save images locally.

val file = File(context.filesDir, "photo_${System.currentTimeMillis()}.jpg")

In this case we are using filesDir in order to have persistent data.

Step 7. Link images to data.

Thought("Stay positive", "/data/user/.../photo.jpg") - store image path 
BitmapFactory.decodeFile(path) - display


Step 8. Update storage on changes.

StorageHelper.save(context, _thoughts.value) - everytime data changes

This ensures data is always up to date and no manual save is required.

 - Futher Discussion

This tutorial demonstrated how to implement an on=device storage feature using internal files and JSON serialization.

Advantages: works offline, simple, no external dependencies, and its secure.

Limitations: not scalable, no built in querying, and manual serialization is required.

Alternative Approaches: Room Database is a simple structured relational stroage, SharedPrefrences is a key-value storage, and finally firebase is a clouse based sync storage.

Source Code: https://github.com/Tcarp2404/CIS357-Semester-Project

 - See Also
android internal storage docs: https://developer.android.com/training/data-storage

Gson documentation: https://google.github.io/gson/UserGuide.html

CameraX guide: https://www.bing.com/search?q=cameraX+guide+android+code&cvid=d20f1312959a4c6eb9885210e794ddb5&gs_lcrp=EgRlZGdlKgYIABBFGDkyBggAEEUYOdIBCDU5ODlqMGo5qAIIsAIB&FORM=ANAB01&PC=U531

Jetpack compose state management: https://www.bing.com/search?q=jetpack+compose+state+management&cvid=5bb37189d5d64760982bedd798475b67&gs_lcrp=EgRlZGdlKgYIABBFGDkyBggAEEUYOdIBCDQ3NDVqMGo5qAIIsAIB&FORM=ANAB01&PC=U531
