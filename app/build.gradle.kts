plugins {
	alias(libs.plugins.androidApplication)
	alias(libs.plugins.jetbrainsKotlinAndroid)
	id("kotlin-parcelize")
	id("kotlin-kapt")
	id("dagger.hilt.android.plugin")
}

android {
	namespace = "com.wahyuhw.authify"
	compileSdk = 34
	
	defaultConfig {
		applicationId = "com.wahyuhw.authify"
		minSdk = 24
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"
		
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		
		val baseUrl: String by project
		val personId: String by project
		buildConfigField(type = "String", "BASE_URL", baseUrl)
		buildConfigField(type = "String", "PERSON_ID", personId)
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
		buildConfig = true
	}
}

dependencies {
	
	// Core
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.appcompat)
	implementation(libs.material)
	implementation(libs.androidx.activity)
	implementation(libs.androidx.constraintlayout)
	
	//UI
	implementation("com.github.ivbaranov:materialfavoritebutton:0.1.5")
	implementation("com.facebook.shimmer:shimmer:0.1.0@aar")
	//ImageLoader
	implementation("de.hdodenhof:circleimageview:3.1.0")
	implementation("com.github.bumptech.glide:glide:4.14.2")
	annotationProcessor("com.github.bumptech.glide:compiler:4.14.2")
	//Lottie
	implementation ("com.airbnb.android:lottie:5.2.0")
	//Coroutine Flow
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
	// Lifecycle
	implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
	implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
	//Retrofit
	implementation("com.squareup.retrofit2:retrofit:2.9.0")
	//Gson Converter
	implementation("com.squareup.retrofit2:converter-gson:2.9.0")
	//Logging Interceptor
	implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.6")
	//Chucker
	debugImplementation("com.github.chuckerteam.chucker:library:3.5.2")
	releaseImplementation("com.github.chuckerteam.chucker:library-no-op:3.5.2")
	//Koin
	implementation("io.insert-koin:koin-android:3.1.6")
	//Rx
	implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
	//MultiStateView
	implementation("com.github.Kennyc1012:MultiStateView:2.2.0")
	//Api 30 supports
	implementation("com.jakewharton.threetenabp:threetenabp:1.4.6")
	//SwipeRefresh
	implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
	//Preference
	implementation("androidx.preference:preference-ktx:1.2.1")
	// Hilt
	implementation("com.google.dagger:hilt-android:2.49")
	kapt("com.google.dagger:hilt-android-compiler:2.49")
	// Pagination
	implementation("ru.alexbykov:nopaginate:0.9.9")
	implementation("ru.alexbykov:norecyclerviewadapter:0.0.2")
	
	// Testing
	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
}