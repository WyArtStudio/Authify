package com.wahyuhw.authify.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor.Builder
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.wahyuhw.authify.data.user.UserDataStore
import com.wahyuhw.authify.data.user.UserRepository
import com.wahyuhw.authify.data.user.source.local.AccountManager
import com.wahyuhw.authify.data.user.source.local.AccountManagerImpl
import com.wahyuhw.authify.data.user.source.network.UserApi
import com.wahyuhw.authify.data.user.source.network.UserApiClient
import com.wahyuhw.authify.data.util.HeaderInterceptor
import com.wahyuhw.authify.domain.user.UserInteractor
import com.wahyuhw.authify.domain.user.UserUseCase
import com.wahyuhw.authify.utils.BASE_URL
import com.wahyuhw.authify.viewmodel.UserViewModel
import io.reactivex.disposables.CompositeDisposable
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
	factory { CompositeDisposable() }
	
	val httpLogging = "http_logging"
	val chuckerLogging = "chucker_logging"
	
	val gson: Gson = GsonBuilder().setLenient().create()
	
	single<Interceptor>(named(httpLogging)) {
		HttpLoggingInterceptor().setLevel(
			if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
			else HttpLoggingInterceptor.Level.NONE
		)
	}
	
	single<Interceptor>(named(chuckerLogging)) { Builder(get()).build() }
	
	fun cache(context: Context): Cache {
		val cacheSize = (5.times(1024).times(1024)).toLong()
		return Cache(context.cacheDir, cacheSize)
	}
	
	single {
		OkHttpClient.Builder()
			.cache(cache(get()))
			.addInterceptor(getHeaderInterceptor())
			.addInterceptor(interceptor = get(named(httpLogging)))
			.addInterceptor(interceptor = get(named(chuckerLogging)))
			.addInterceptor { chain ->
				val request = chain.request()
				request.newBuilder().header("Cache-Control", "public, max-age=" + 10).build()
				chain.proceed(request)
			}
			.connectTimeout(120, TimeUnit.SECONDS)
			.build()
	}
	
	single<UserApiClient> {
		Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(GsonConverterFactory.create(gson))
			.client(get())
			.build()
			.create(UserApiClient::class.java)
	}
	
	single {
		UserApi(get())
	}
}

val repositoryModule = module {
	single<UserRepository> { UserDataStore(get()) }
}

val useCaseModule = module {
	single<UserUseCase> { UserInteractor(get()) }
}

val viewModelModule = module {
	viewModel { UserViewModel(get(), get()) }
}

val preferenceModule = module {
	single<AccountManager> { AccountManagerImpl(get()) }
}

private fun getHeaderInterceptor(): Interceptor {
	val headers = HashMap<String, String>()
	return HeaderInterceptor(headers)
}