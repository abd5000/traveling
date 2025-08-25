package com.example.myadmin.home.addtrip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myadmin.R
import com.example.myadmin.databinding.ListSheetBinding
import com.example.myadmin.home.addtrip.buss.Data


class BusAdabter(private val item: List<Data?>, private val listener: BusClickLestener) :RecyclerView.Adapter<BusAdabter.ViewHolderHome>(){
    class ViewHolderHome(itemView: View):RecyclerView.ViewHolder(itemView) {
        val binding= ListSheetBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHome {
       val view=LayoutInflater.from(parent.context).inflate(R.layout.list_sheet,parent,false)
        return ViewHolderHome(view)
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: ViewHolderHome, position: Int) {
        val view=item[position]
        holder.binding.selectCity.text= "${view!!.number},${view.type}"
        holder.binding.selectCity.setOnClickListener{
            listener.onBusClick(view)
        }
    }
}