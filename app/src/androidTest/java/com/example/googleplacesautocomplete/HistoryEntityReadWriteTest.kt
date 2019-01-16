package com.example.googleplacesautocomplete

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.googleplacesautocomplete.db.AppDatabase
import com.example.googleplacesautocomplete.db.HistoryEntity
import io.reactivex.functions.Predicate
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class HistoryEntityReadWriteTest {

    @JvmField @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        val history = HistoryEntity(1, "name", "address", 0.0, 0.0)
        database.historyDao.apply {
            getHistory().test().assertValue {
                it.isEmpty()
            }
            insertHistory(history).test().assertResult()
            getHistory().test().assertValue {
                it.isNotEmpty() && it[0].name.compareTo(history.name) == 0
            }
            deleteHistory(history).test().assertResult()
            getHistory().test().assertValue {
                it.isEmpty()
            }
        }
    }
}