package com.example.mytestnav.trips.filter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytestnav.R
import com.example.mytestnav.databinding.ListSheetBinding

class FilterAdabtar(private val list: List<String>, private val listen:TypeFilterClick): RecyclerView.Adapter<FilterAdabtar.ViewHolderFilter>() {
    class ViewHolderFilter(item:View):RecyclerView.ViewHolder(item){
        val binding= ListSheetBinding.bind(itemView)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilterAdabtar.ViewHolderFilter {
      val view=LayoutInflater.from(parent.context).inflate(R.layout.list_sheet,parent,false)
        return ViewHolderFilter(view)
    }

    override fun onBindViewHolder(holder: FilterAdabtar.ViewHolderFilter, position: Int) {
        val view= list[position]
        //اسم الفلترة وليس اسم الشركة
        holder.binding.selectCity.text= view
        holder.binding.selectCity.setOnClickListener{
            listen.onTypeClick(view)
        }
    }

    override fun getItemCount()=list.size
}
