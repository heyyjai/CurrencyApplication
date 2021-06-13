package com.example.currencyApplication.currencyList.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyApplication.currencyList.viewModel.CurrencyListViewModel
import com.example.currencyApplication.R
import com.example.currencyApplication.currencyList.model.CurrencyInfo
import com.example.currencyApplication.currencyList.adapter.CurrencyListAdapter
import kotlinx.android.synthetic.main.fragment_currency_list.*

abstract class CurrencyListFragment() : Fragment() {


    private lateinit var currencyListAdapter: CurrencyListAdapter
    private lateinit var viewModel: CurrencyListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = activity?.let {
            ViewModelProviders.of(it).get(CurrencyListViewModel::class.java)
        } ?: run {
            throw Exception("Invalid Activity")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_currency_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (::viewModel.isInitialized) {
            viewModel.displayList.observe(viewLifecycleOwner, Observer {
                if (it.isNullOrEmpty().not()) {
                    setAdapter(it.toMutableList())
                }
            })
        }
    }

    private fun setAdapter(data: MutableList<CurrencyInfo>) {
        rvCurrencyList?.post {
            if (::currencyListAdapter.isInitialized) {
                currencyListAdapter.refreshList(data)
            } else {
                val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                currencyListAdapter =
                    CurrencyListAdapter(
                        data,
                        object : CurrencyListAdapter.CurrencyItemClickListener {
                            override fun onCurrencyItemClick(item: CurrencyInfo) {
                                Toast.makeText(
                                    context,
                                    "You've clicked ${item.name}",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }

                        })
                rvCurrencyList?.adapter = currencyListAdapter
                rvCurrencyList?.layoutManager = layoutManager
            }
        }
    }
}