package com.example.googleplacesautocomplete.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.googleplacesautocomplete.db.HistoryDao
import com.example.googleplacesautocomplete.db.HistoryEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class HistoryViewModel @Inject constructor(
    private val historyDao: HistoryDao,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    val historyLiveData = MutableLiveData<MutableList<HistoryEntity>>()

    init {
        loadFromDatabase()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private fun loadFromDatabase() {
        compositeDisposable.add(
            historyDao.getHistory()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onHistoryFetched, this::onError)
        )
    }

    fun insertHistory(history: HistoryEntity) {
        compositeDisposable.add(
            historyDao.insertHistory(history)
                .subscribeOn(Schedulers.newThread())
                .subscribe()
        )
        historyLiveData.value?.add(history)
    }

    fun deleteHistory(position: Int) {
        if (historyLiveData.value != null) {
            compositeDisposable.add(
                historyDao.deleteHistory(historyLiveData.value!![position])
                    .subscribeOn(Schedulers.newThread())
                    .subscribe()
            )
            historyLiveData.value!!.removeAt(position)
        }
    }

    private fun onError(throwable: Throwable) {
        Log.d("HistoryViewModel", throwable.message)
    }

    private fun onHistoryFetched(history: List<HistoryEntity>) {
        historyLiveData.value = history.toMutableList()
    }
}