package com.example.currencyApplication.currencyList.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "currency_info_table")
data class CurrencyInfo(
    @PrimaryKey var id: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "symbol") var symbol: String
)