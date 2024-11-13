pluginManagement {
    repositories {
        google()  // Required for Android plugins
        mavenCentral()  // Required for Kotlin and other dependencies
        gradlePluginPortal()  // Ensure this is included for resolving plugins
    }
}

dependencyResolutionManagement {
    repositories {
        google()  // Google repository for dependencies
        mavenCentral()  // Maven Central repository for dependencies
    }
}

rootProject.name = "SafePathProject"
include(":app")
