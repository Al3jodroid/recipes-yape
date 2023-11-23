plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id ("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}


android {
    namespace = "com.al3jodroid.recipes"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.al3jodroid.recipes"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    var apiScheme = "\"https:"
    var apiURL = "www.themealdb.com/api/json/v1/1/\""
    var apiKeyValue = "\"1\""

    buildTypes {
        debug {
            buildConfigField(type = "String", name = "API_SCHEME", value = apiScheme + "\"")
            buildConfigField(type = "String", name = "API_URL", value = apiScheme + apiURL)
            buildConfigField(type = "String", name = "API_KEY_VALUE", value = apiKeyValue)
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(type = "String", name = "API_SCHEME", value = apiScheme)
            buildConfigField(type = "String", name = "API_URL", value = apiScheme + apiURL)
            buildConfigField(type = "String", name = "API_KEY_VALUE", value = apiKeyValue)
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
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //dependency injection libraries
    implementation("com.google.dagger:hilt-android:2.48.1")
    kapt("com.google.dagger:hilt-android-compiler:2.48.1")

    //add viewmodel libraries
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0-alpha01")

    //add livedata libraries for states
    implementation("androidx.compose.runtime:runtime-livedata:1.1.0")

    //http external connections libraries
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.7.2")

    // Android maps Compose for Android sdk
    implementation("com.google.maps.android:maps-compose:4.1.1")

    //library for loading images
    implementation("io.coil-kt:coil-compose:2.5.0")

}

kapt {
    correctErrorTypes = true
}