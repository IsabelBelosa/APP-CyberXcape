plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.example.cyberxcape"
    compileSdk = 35


    defaultConfig {
        applicationId = "com.example.cyberxcape"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        // Necesario para tests instrumentados
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    testOptions {
        animationsDisabled = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.6.0"
    }
}

dependencies {
    // Jetpack Compose y temas
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.v121)
    implementation(libs.androidx.navigation.compose)

    // JSON / Network
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Google Maps
    implementation(libs.play.services.maps)
    implementation(libs.play.services.maps.v1810)
    implementation(libs.maps.compose)

    // Glance (opcional)
    implementation(libs.glance.appwidget)
    implementation(libs.glance.material3)

    // TESTING

    // Unit tests
    testImplementation(libs.junit)
    testImplementation(kotlin("test"))

    // UI Tests (solo uno por tipo)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // UI test tooling (debug only)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
