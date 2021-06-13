package com.example.currencyApplication.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.currencyApplication.database.dao.CurrencyInfoDao
import com.example.currencyApplication.currencyList.model.CurrencyInfo

@Database(entities = arrayOf(CurrencyInfo::class),version =1)
abstract class ApplicationRoomDb:RoomDatabase() {
    abstract fun currencyInfoDao():CurrencyInfoDao
}