package com.example.mytestnav.trips.filter.typeTrips

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytestnav.R
import com.example.mytestnav.databinding.ListSheetBinding

class AdapterTypsTrips(private val list: List<DataX?>?, private val listen: TypeTripLestenar):RecyclerView.Adapter<AdapterTypsTrips.ViewHolderFilter>() {
    class ViewHolderFilter(item:View):RecyclerView.ViewHolder(item) {
        val binding= ListSheetBinding.bind(itemView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterTypsTrips.ViewHolderFilter {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.list_sheet,parent,false)
        return ViewHolderFilter(view)
    }

    override fun onBindViewHolder(holder: AdapterTypsTrips.ViewHolderFilter, position: Int) {
        val type= list!![position]

        holder.binding.selectCity.text= type!!.type
        holder.binding.selectCity.setOnClickListener{
            listen.onTypeClick(type)
        }
    }

    override fun getItemCount()=list!!.size
}