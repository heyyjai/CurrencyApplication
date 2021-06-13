package com.example.currencyApplication.database.dao

import androidx.room.*
import com.example.currencyApplication.currencyList.model.CurrencyInfo
import io.reactivex.Single

@Dao
interface CurrencyInfoDao {

    @Query("SELECT * FROM currency_info_table")
    fun getAll(): Single<List<CurrencyInfo>>

    @Query("SELECT * FROM currency_info_table ORDER BY symbol DESC ")
    fun getListSortedBySymbolDesc(): Single<List<CurrencyInfo>>

    @Query("SELECT * FROM currency_info_table ORDER BY symbol ASC ")
    fun getListSortedBySymbolAsc(): Single<List<CurrencyInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(currencyInfoList: List<CurrencyInfo>): Single<List<Long>>

    @Delete
    fun delete(currencyInfo: CurrencyInfo)
}