package com.example.currencyApplication.currencyList.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyApplication.currencyList.model.CurrencyInfo
import com.example.currencyApplication.currencyList.repository.CurrencyRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import java.lang.Thread.currentThread

class CurrencyListViewModel : ViewModel() {


    companion object {
        enum class SortType {
            DEFAULT,
            DESC,
            ASC
        }
    }

    var sortBtnType = SortType.DEFAULT

    val displayList: MutableLiveData<List<CurrencyInfo>> by lazy {
        MutableLiveData<List<CurrencyInfo>>()
    }

    private val allDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }
    private val currencyRepository = CurrencyRepository()

    private var tempList: MutableList<CurrencyInfo> = mutableListOf()

    fun getCurrencyListFromDB(context: Context) {

        currencyRepository.getCurrencyListFromDB().subscribe({ itemList ->
            if (itemList.isNullOrEmpty()) {
                getCurrencyListApiCall(context)
            } else {
                displayList.postValue(itemList)
                tempList.clear()
                tempList.addAll(itemList)
                currencyRepository.saveCurrencyList(itemList)
            }
        },
            {
                Toast.makeText(context, "Request Error", Toast.LENGTH_LONG).show()
            }).addTo(allDisposable)
    }

    private fun getCurrencyListApiCall(context: Context) {
        val itemList = currencyRepository.getCurrencyList(context)
        tempList.clear()
        tempList.addAll(itemList)
        displayList.postValue(itemList)
        currencyRepository.saveCurrencyList(itemList)
    }


    fun getSortedList(sortType: SortType) {
        when (sortType) {
            SortType.DEFAULT, SortType.DESC -> {
                tempList.sortedByDescending { it.name }
            }
            SortType.ASC -> {
                tempList.sortedBy { it.name }
            }
        }.run {
            displayList.postValue(this)
            Log.d("CurrencyListViewModel", "Consuming item data on: ${currentThread().name}")
        }

    }

    override fun onCleared() {
        super.onCleared()
        allDisposable.clear()
    }
}