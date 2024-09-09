package com.test.mangofzcotest.di

import android.content.Context
import com.google.gson.Gson
import com.test.mangofzcotest.data.database.dao.UserDao
import com.test.mangofzcotest.data.database.root.AppDatabase
import com.test.mangofzcotest.data.network.apiservice.ApiService
import com.test.mangofzcotest.data.network.apiservice.RefreshTokenApiService
import com.test.mangofzcotest.data.network.authenticator.TokenAuthenticator
import com.test.mangofzcotest.data.network.interceptors.ErrorInterceptor
import com.test.mangofzcotest.data.network.interceptors.LoggingInterceptorLogger
import com.test.mangofzcotest.data.network.interceptors.TokenInterceptor
import com.test.mangofzcotest.data.preferencesimpl.PreferencesManagerImpl
import com.test.mangofzcotest.data.repositoryimpl.AuthRepositoryImpl
import com.test.mangofzcotest.data.repositoryimpl.TokenRepositoryImpl
import com.test.mangofzcotest.data.repositoryimpl.UserRepositoryImpl
import com.test.mangofzcotest.domain.repository.AuthRepository
import com.test.mangofzcotest.domain.repository.TokenRepository
import com.test.mangofzcotest.domain.repository.UserRepository
import com.test.mangofzcotest.domain.storage.PreferencesManager
import com.test.mangofzcotest.domain.usecases.auth.RefreshAccessTokenUseCase
import com.text.mangofzcotest.core.utils.isReleaseVersion
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.ConnectionPool
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [DataModule.BindsModule::class])
@InstallIn(SingletonComponent::class)
class DataModule {

    companion object {
        private const val ERROR_INTERCEPTOR = "error_interceptor"
        private const val TOKEN_INTERCEPTOR = "token_interceptor"
        private const val TOKEN_AUTHENTICATOR = "token_authenticator"
        private const val HTTP_LOGGING_INTERCEPTOR = "http_logging_interceptor"
        private const val API_V1_URL = "https://plannerok.ru/api/v1/"
        private const val API_V1 = "api_v1"
        private const val API_V1_WITHOUT_AUTHENTICATOR = "api_v1_without_client"
        private const val CLIENT_WITHOUT_AUTHENTICATOR = "client_without_authenticator"
        private const val TIMEOUT = 30L
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    @Named(ERROR_INTERCEPTOR)
    fun provideErrorInterceptor(gson: Gson): Interceptor = ErrorInterceptor(gson)

    @Provides
    @Singleton
    @Named(TOKEN_INTERCEPTOR)
    fun provideTokenInterceptor(
        preferencesManager: PreferencesManagerImpl
    ): Interceptor = TokenInterceptor(preferencesManager)

    @Provides
    @Singleton
    @Named(TOKEN_AUTHENTICATOR)
    fun provideTokenAuthenticator(
        preferencesManager: PreferencesManager,
        refreshAccessTokenUseCase: RefreshAccessTokenUseCase
    ) : Authenticator = TokenAuthenticator(preferencesManager, refreshAccessTokenUseCase)

    @Provides
    @Singleton
    @Named(HTTP_LOGGING_INTERCEPTOR)
    fun provideHttpLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor(LoggingInterceptorLogger()).apply {
            level = if (!isReleaseVersion) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }


    @Provides
    @Singleton
    fun provideOkHttpClient(
        @Named(ERROR_INTERCEPTOR) errorInterceptor: Interceptor,
        @Named(TOKEN_INTERCEPTOR) tokenInterceptor: Interceptor,
        @Named(HTTP_LOGGING_INTERCEPTOR) httpLoggingInterceptor: Interceptor,
        @Named(TOKEN_AUTHENTICATOR) tokenAuthenticator: Authenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .authenticator(tokenAuthenticator)
            .addInterceptor(errorInterceptor)
            .addInterceptor(tokenInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .retryOnConnectionFailure(true)
            .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
            .build()
    }

    @Provides
    @Singleton
    @Named(CLIENT_WITHOUT_AUTHENTICATOR)
    fun provideOkHttpClientWithoutAuthenticator(
        @Named(ERROR_INTERCEPTOR) errorInterceptor: Interceptor,
        @Named(TOKEN_INTERCEPTOR) tokenInterceptor: Interceptor,
        @Named(HTTP_LOGGING_INTERCEPTOR) httpLoggingInterceptor: Interceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(errorInterceptor)
            .addInterceptor(tokenInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .retryOnConnectionFailure(true)
            .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(@Named(API_V1) retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRefreshTokenApiService(@Named(API_V1_WITHOUT_AUTHENTICATOR) retrofit: Retrofit): RefreshTokenApiService {
        return retrofit.create(RefreshTokenApiService::class.java)
    }

    @Provides
    @Singleton
    @Named(API_V1)
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_V1_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named(API_V1_WITHOUT_AUTHENTICATOR)
    fun provideRetrofitWithoutAuthenticator(@Named(CLIENT_WITHOUT_AUTHENTICATOR) client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_V1_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }


    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {
        @Binds
        @Singleton
        fun bindsAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

        @Binds
        @Singleton
        fun bindsUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

        @Binds
        @Singleton
        fun bindsTokenRepository(tokenRepositoryImpl: TokenRepositoryImpl): TokenRepository

        @Binds
        @Singleton
        fun bindsPreferencesManager(preferencesManagerImpl: PreferencesManagerImpl): PreferencesManager

    }
}
