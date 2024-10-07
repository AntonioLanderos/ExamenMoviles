plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.feature.examenmoviles"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.feature.examenmoviles"
        minSdk = 29
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.androidx.cardview) // Or latest version

    //Glide
    implementation(libs.glide)
    annotationProcessor(libs.compiler)
    implementation(libs.glide.transformations)

    //Corrutinas
    implementation(libs.kotlinx.coroutines.android)

    //Fragment
    implementation(libs.androidx.fragment.ktx)
    //Activity
    implementation(libs.androidx.activity.ktx)
    //Data binding
    implementation(libs.androidx.databinding.runtime)

}