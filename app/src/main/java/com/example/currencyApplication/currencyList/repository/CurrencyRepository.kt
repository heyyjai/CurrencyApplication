package com.example.currencyApplication.currencyList.repository

import android.content.Context

import com.example.currencyApplication.database.dao.CurrencyInfoDao
import com.example.currencyApplication.database.db.ApplicationDBHelper
import com.example.currencyApplication.currencyList.model.CurrencyInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.io.InputStream

class CurrencyRepository {

    private val mDao: CurrencyInfoDao by lazy {
        ApplicationDBHelper.currencyInfoDao()
    }

    fun getCurrencyList(context: Context): List<CurrencyInfo> {
        val jsonString = loadJSONFromAsset(context)
        val arrayCurrencyInfoType = object : TypeToken<List<CurrencyInfo>>() {}.type
        return Gson().fromJson(jsonString, arrayCurrencyInfoType)
    }

    fun getCurrencyListFromDB(): Observable<List<CurrencyInfo>> {
        return mDao.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toObservable()
    }

    fun saveCurrencyList(currencyList: List<CurrencyInfo>) {
        mDao.saveAll(currencyList)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toObservable()
            .subscribe()
    }


    private fun loadJSONFromAsset(context: Context): String? {
        var json: String? = null
        json = try {
            val input: InputStream = context.assets.open("currencyList.json")
            val size: Int = input.available()
            val buffer = ByteArray(size)
            input.read(buffer)
            input.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}