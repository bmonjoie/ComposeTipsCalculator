plugins {
    id("com.android.application")
    kotlin("android")
}

val kotlinVersion: String by project
val composeVersion: String by project

android {
    compileSdkVersion(30)

    defaultConfig {
        applicationId = "be.xzan.composetipscalculator"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
    }

}

dependencies {
    implementation("androidx.appcompat:appcompat:1.3.0-beta01")
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.0-alpha05")
    implementation("androidx.activity:activity-compose:1.3.0-alpha04")
}
