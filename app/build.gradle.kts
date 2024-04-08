plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.weather"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.example.weather"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    //noinspection UseTomlInstead
    implementation ("com.intuit.sdp:sdp-android:1.1.0")
    implementation(libs.activity)
    //noinspection UseTomlInstead
    implementation ("com.airbnb.android:lottie:4.1.0")
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    //noinspection UseTomlInstead
    implementation("com.google.android.gms:play-services-auth:21.0.0")
    //noinspection UseTomlInstead
    implementation("com.aurelhubert:ahbottomnavigation:2.0.4")
    implementation(libs.firebase.storage)
    implementation ("com.squareup.okhttp3:okhttp:4.9.1")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    implementation(libs.firebase.firestore)
    implementation(libs.play.services.location)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.squareup.retrofit2:retrofit:2.10.0")
    implementation ("com.google.code.gson:gson:2.10.1")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
}