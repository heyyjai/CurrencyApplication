package com.example.currencyApplication.application

import android.app.Application
import com.example.currencyApplication.database.db.ApplicationDBHelper

class MyApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        ApplicationDBHelper.init(this)
    }
}