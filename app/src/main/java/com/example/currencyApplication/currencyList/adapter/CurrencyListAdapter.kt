package com.example.currencyApplication.currencyList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyApplication.currencyList.model.CurrencyInfo
import com.example.currencyApplication.databinding.RowCurrencyinfoItemBinding

class CurrencyListAdapter(
    data: MutableList<CurrencyInfo>,
    private val itemClickListener: CurrencyItemClickListener
) : RecyclerView.Adapter<CurrencyListAdapter.CurrencyListViewHolder>() {

    interface CurrencyItemClickListener {
        fun onCurrencyItemClick(item: CurrencyInfo)
    }

    private var displayList: MutableList<CurrencyInfo> = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyListViewHolder {
        val binding = RowCurrencyinfoItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CurrencyListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyListViewHolder, position: Int) {
        val item = displayList[position]
        holder.bind(item)

        holder.binding.tvCurrencyIconHint.text = item.name.substring(0, 1)

        holder.binding.rootLayout.setOnClickListener {
            itemClickListener.onCurrencyItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return displayList.size
    }

    fun refreshList(list: MutableList<CurrencyInfo>) {
        displayList.clear()
        displayList.addAll(list)
        notifyDataSetChanged()
    }

    inner class CurrencyListViewHolder(val binding: RowCurrencyinfoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CurrencyInfo) {
            binding.currencyInfo = item
        }
    }
}



