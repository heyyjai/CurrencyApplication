package com.example.currencyApplication.currencyList.view

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.currencyApplication.R
import com.example.currencyApplication.currencyList.viewModel.CurrencyListViewModel
import com.example.currencyApplication.currencyList.viewModel.CurrencyListViewModel.Companion.SortType

class DemoActivity : AppCompatActivity() {

    companion object {
        const val MIN_CLICK_INTERVAL = 1000
    }

    private lateinit var viewModel: CurrencyListViewModel
    private var mLastClickedSortBtnTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        val showAllBtn = findViewById<TextView>(R.id.tvShowAll)
        val sortingBtn = findViewById<ImageView>(R.id.ivSorting)
        viewModel = ViewModelProviders.of(this).get(CurrencyListViewModel::class.java)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, DemoFragment())
                .commitNow()
        }

        showAllBtn.setOnClickListener {
            viewModel.getCurrencyListFromDB(this)
            sortingBtn.visibility = View.VISIBLE
            showAllBtn.visibility = View.GONE
        }

        sortingBtn.setOnClickListener {
            Log.d(
                "DemoActivity",
                "Clicked Interval = ${SystemClock.elapsedRealtime() - mLastClickedSortBtnTime}"
            )
            if (SystemClock.elapsedRealtime() - mLastClickedSortBtnTime < MIN_CLICK_INTERVAL) {
                Toast.makeText(this, "Too fast to sort again", Toast.LENGTH_SHORT).show()
            } else {
                synchronized(SystemClock.elapsedRealtime() - mLastClickedSortBtnTime < MIN_CLICK_INTERVAL) {
                    val modifiedSortType = when (viewModel.sortBtnType) {
                        SortType.ASC -> {
                            SortType.DESC
                        }
                        SortType.DESC -> {
                            SortType.ASC
                        }
                        SortType.DEFAULT -> {
                            SortType.DESC
                        }
                    }
                    viewModel.sortBtnType = modifiedSortType
                    viewModel.getSortedList(modifiedSortType)
                    mLastClickedSortBtnTime = SystemClock.elapsedRealtime()
                }
            }
        }
    }
}