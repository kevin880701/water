plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-android")
    id ("kotlin-kapt")
    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
    // Add the Crashlytics Gradle plugin
    id("com.google.firebase.crashlytics")
}

android {


    signingConfigs {
        getByName("debug") {
            storeFile = file("../lhrkey.jks")
            storePassword = "W@lf6875"
            keyAlias = "key0"
            keyPassword = "W@lf6875"
        }
        create("release") {
            storeFile = file("../lhrkey.jks")
            storePassword = "W@lf6875"
            keyAlias = "key0"
            keyPassword = "W@lf6875"
        }
    }

    namespace = "com.lhr.water"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.lhr.water"
        minSdk = 26
        targetSdk = 33
        versionCode = 8
        versionName = "1.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = false
        }
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            isDebuggable = true
        }
    }
    applicationVariants.all {
        val variant = this
        variant.outputs
            .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
            .forEach { output ->
                val outputFileName = "Water_${variant.baseName}_${variant.versionName}+${variant.versionCode}.apk"
                println("OutputFileName: $outputFileName")
                output.outputFileName = outputFileName
            }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "18"
    }
    viewBinding {
        enable = true
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}
dependencies {
    val activity_version = "1.2.3"
    val lifecycle = "2.2.0"
    val room = "2.2.1"
    val fragment_version = "1.3.5"
    val okhttp3 = "4.9.0"
    val retrofit2 = "2.9.0"
    val glide = "4.12.0"

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    // viewModel
    implementation ("androidx.lifecycle:lifecycle-extensions:$lifecycle")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation ("androidx.activity:activity-ktx:$activity_version")
    implementation ("androidx.fragment:fragment-ktx:$fragment_version")
    implementation ("com.google.android.material:material:1.4.0")
    //room
    implementation ("androidx.room:room-runtime:$room")
    annotationProcessor ("androidx.room:room-compiler:$room")
    testImplementation ("androidx.room:room-testing:$room")
    kapt ("androidx.room:room-compiler:$room")
    implementation ("androidx.room:room-ktx:$room")
    //CircleImageView
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-analytics")
    // Timber
    implementation ("com.jakewharton.timber:timber:5.0.1")
    //rxjava2
    implementation ("io.reactivex.rxjava3:rxjava:3.1.8")
    implementation ("io.reactivex.rxjava3:rxandroid:3.0.2")
    implementation ("com.squareup.retrofit2:adapter-rxjava3:2.9.0")
    //barcode
    implementation ("com.journeyapps:zxing-android-embedded:4.3.0")

    //okhttp3
    implementation ("com.squareup.okhttp3:okhttp:$okhttp3")
    implementation ("com.squareup.okhttp3:logging-interceptor:$okhttp3")
    //retrofit2
    implementation ("com.squareup.retrofit2:retrofit:$retrofit2")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofit2")
    implementation ("com.squareup.retrofit2:adapter-rxjava2:2.4.0")

    //Glide
    implementation ("com.github.bumptech.glide:glide:$glide")
    annotationProcessor ("com.github.bumptech.glide:compiler:$glide")

    implementation ("com.google.code.gson:gson:2.8.9")
    implementation ("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.10")

    implementation ("androidx.paging:paging-runtime:3.1.1")
}