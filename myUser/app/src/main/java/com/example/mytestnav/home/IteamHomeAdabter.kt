package com.example.mytestnav.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytestnav.R
import com.example.mytestnav.databinding.ListSheetBinding
import com.example.mytestnav.home.cites.Data

class IteamHomeAdabter(private val item: List<Data?>, private val listener: CityClickLestener) :RecyclerView.Adapter<IteamHomeAdabter.ViewHolderHome>(){
    class ViewHolderHome(itemView: View):RecyclerView.ViewHolder(itemView) {
        val binding=ListSheetBinding.bind(itemView)
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
        holder.binding.selectCity.text=view?.name?.trim()
        holder.binding.selectCity.setOnClickListener{
            listener.onItemClick(view!!)
        }
    }
}