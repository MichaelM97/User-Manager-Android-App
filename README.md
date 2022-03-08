
# User Management App

A simple app that displays a list of users from https://gorest.co.in/, allows you to add new users, and remove existing ones by long pressing.

Built using Android Studio Bumblebee | 2021.1.1

## Setup

### First-time build issue
There is an issue when first building this project which is caused by the `kotlin-dsl-precompiled-script-plugins` plugin. For now, the following workaround exists:

0. Please ensure that you have configured Android Studio to build this app using Java 11 first (see below)
1. When you first clone the repository, you will need to comment-out the `android` configuration blocks in the following Gradle scripts (located in the `buildSrc` folder):
   * `common.gradle.kts`
   * `commonFeature.gradle.kts`
2. Once you have done this, run a Gradle sync and wait for it to fail
3. Once it has failed, uncomment the `android` configuration blocks and Gradle sync again
4. The project will now build successfully

### Ensure that you build the app using Java 11

`Android Studio -> Settings/Preferences -> Build, Execution, Deployment -> Build Tools -> Gradle -> Set "Gradle JDK" to '11'`

### Add your Go REST API key

Update the `GO_REST_KEY` value in `keys.properties` with your API key from https://gorest.co.in/

## Module structure

* `:app` - Code that launches the app & integration tests
* `:buildSrc` - Gradle configuration that will be used in all modules (e.g. dependencies, versions, plugins, etc.)
* `:data` - Repository interfaces which are exposed publicly and their implementations which are kept internal & bound using DI
* `:network` - Service interfaces for communicating over the network which are built internally & provided using DI
* `:userList:feature` - View layer for the user list feature (composables, viewmodels, etc.)
* `:userList:domain` - Domain layer for the user list feature (usecases)
* `:core:ui` - UI elements/extensions that are/can be shared across the application
* `:core:models` - Model classes that are used throughout the application
* `:core:extensions` - Extension functions that are/can be used throughout the application
* `:core:test` - Unit test utilities, also exposes all required unit test dependencies
* `:core:uitest` - Integration test utilities, also exposes all required integration test dependencies

## Tools/frameworks used

* **MVVM** design pattern
* **Jetpack Compose** for UI
* **Hilt** for dependency injection
* **Kotlin Coroutines** for asynchronous programming
* **Retrofit** for building network services
* **OkHttp** for building HTTP clients
* **JUnit5** for unit tests
