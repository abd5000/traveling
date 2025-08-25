package com.example.myadmin.usersandbuss.addbuss.typBus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myadmin.R
import com.example.myadmin.databinding.ListSheetBinding


class IteamTypeAdabter(private val item: MutableList<Data?>, private val listener: TypeClickLestener) :RecyclerView.Adapter<IteamTypeAdabter.ViewHolderHome>(){
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
        holder.binding.selectCity.text= view!!.type?.trim()
        holder.binding.selectCity.setOnClickListener{
            listener.onTypeClick(view)
        }
    }
}