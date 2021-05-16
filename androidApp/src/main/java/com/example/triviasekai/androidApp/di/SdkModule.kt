package com.example.triviasekai.androidApp.di

import android.content.Context
import com.example.triviasekai.shared.TriviaSDK
import com.example.triviasekai.shared.cache.DatabaseDriverFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object SdkModule {

    @Provides
    fun provideTriviaSDK(@ApplicationContext context: Context): TriviaSDK {
        return TriviaSDK(DatabaseDriverFactory(context))
    }
}