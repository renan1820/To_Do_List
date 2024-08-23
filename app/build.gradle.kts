plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
    kotlin("kapt")

}

android {
    namespace = "com.renan.portifolio.to_dolist"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.renan.portifolio.to_dolist"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"
        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
        }

        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        create("mock"){
            initWith(getByName("debug"))
            applicationIdSuffix = ".debugStaging"
        }
    }

    flavorDimensions.add("hardware")

    productFlavors {
        create("phone"){
            dimension = "hardware"
//            apply(from = "src/phone/build-phone.gradle")
        }

        create("nativeTotem") {
            dimension = "hardware"
//            apply(from = "src/nativeTotem/build-nativetotem.gradle")
        }

    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.google.generativeai)
    implementation(libs.androidx.multidex)
    implementation(libs.google.services)
    implementation(libs.google.accompanist)
    implementation(libs.lottie.compose)
    implementation(libs.http3.okhttp)
    implementation(libs.http3.logging)
    implementation(libs.koin.test)
    implementation(libs.koin.test.junit)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.mockito)
    testImplementation(libs.mockito.core)
    testImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}