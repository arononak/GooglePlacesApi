package com.example.googleplacesautocomplete.db

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history")
    fun getHistory(): Flowable<List<HistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(history: HistoryEntity): Completable

    @Delete
    fun deleteHistory(history: HistoryEntity): Completable
}