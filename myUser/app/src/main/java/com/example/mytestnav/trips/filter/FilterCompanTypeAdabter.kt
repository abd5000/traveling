package com.example.mytestnav.trips.filter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytestnav.R
import com.example.mytestnav.databinding.ListSheetBinding
import com.example.mytestnav.trips.companys.Data

class FilterCompanTypeAdabter(private val list: List<Data?>?, private val listen:FilterClickListener):RecyclerView.Adapter<FilterCompanTypeAdabter.ViewHolderFilter>() {
    class ViewHolderFilter(item: View):RecyclerView.ViewHolder(item) {
        val binding= ListSheetBinding.bind(itemView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilterCompanTypeAdabter.ViewHolderFilter {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.list_sheet,parent,false)
        return ViewHolderFilter(view)
    }

    override fun onBindViewHolder(holder: FilterCompanTypeAdabter.ViewHolderFilter, position: Int) {
        val view= list!![position]

        holder.binding.selectCity.text= view!!.name
        holder.binding.selectCity.setOnClickListener{
            listen.onItemClick(view)
        }
    }

    override fun getItemCount()=list!!.size
}