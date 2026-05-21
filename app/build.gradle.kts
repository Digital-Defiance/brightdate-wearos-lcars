import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
}

// Load release signing credentials from a local, gitignored file.
val keystoreProperties = Properties().apply {
    val file = rootProject.file("keystore.properties")
    if (file.exists()) load(file.inputStream())
}

android {
    // Tell AGP this is a no-code project (WFF watch face).
    // This prevents dex generation entirely.
    // enableKotlin = false // This property is not recognized by AGP and is causing an error.
    // The `android:hasCode="false"` in AndroidManifest.xml already indicates no code.

    namespace = "org.digitaldefiance.brightdate.lcars"
    compileSdk = 35

    defaultConfig {
        applicationId = "org.digitaldefiance.brightdate.lcars"
        minSdk = 35
        targetSdk = 35
        versionCode = 12
        versionName = "1.1.1"
    }

    signingConfigs {
        create("release") {
            if (keystoreProperties.isNotEmpty()) {
                storeFile = rootProject.file(keystoreProperties.getProperty("storeFile"))
                storePassword = keystoreProperties.getProperty("storePassword")
                keyAlias = keystoreProperties.getProperty("keyAlias")
                keyPassword = keystoreProperties.getProperty("keyPassword")
            }
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false // Set to false for debug builds
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = false
            signingConfig = if (keystoreProperties.isNotEmpty()) {
                signingConfigs.getByName("release")
            } else {
                signingConfigs.getByName("debug")
            }
        }
    }
}
