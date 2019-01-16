package com.example.googleplacesautocomplete.di

import android.app.Application
import androidx.room.Room
import com.example.googleplacesautocomplete.db.AppDatabase
import com.example.googleplacesautocomplete.db.HistoryDao
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    fun providesRoomDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "app-database").build()
    }

    @Singleton
    @Provides
    fun providesHistoryDao(appDatabase: AppDatabase): HistoryDao {
        return appDatabase.historyDao
    }

    @Singleton
    @Provides
    fun providesCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }
}