package com.example.currencyApplication.database.db

import android.content.Context
import androidx.room.Room


object ApplicationDBHelper {
    lateinit var db: ApplicationRoomDb
    lateinit var context: Context

    fun init(context: Context){
        this.context = context
        db = Room.databaseBuilder(
            context,
            ApplicationRoomDb::class.java,
            "currency_info_table"
        ).build()

    }

    fun currencyInfoDao() = db.currencyInfoDao()
}