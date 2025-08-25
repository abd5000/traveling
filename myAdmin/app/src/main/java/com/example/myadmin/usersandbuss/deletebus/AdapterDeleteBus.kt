package com.example.myadmin.usersandbuss.deletebus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myadmin.R

import com.example.myadmin.databinding.BusItemBinding
import com.example.myadmin.usersandbuss.deletebus.allbuss.Data


class AdapterDeleteBus(private val list: List<Data?>?, private val listener:DeleteBusListener): RecyclerView.Adapter<AdapterDeleteBus.TripsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripsHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.bus_item,parent,false)
        return TripsHolder(view)
    }

    override fun getItemCount()=list!!.size

    override fun onBindViewHolder(holder: TripsHolder, position: Int) {
        val cur= list!![position]
        holder.binding.apply {
           bus.text="${cur!!.number}"
            type.text="${cur.type}"
            driver.text="${cur.driver}"
        }

        holder.binding.deleteBtn.setOnClickListener {
            listener.onReserveBus(list[position]!!,position)
        }
        holder.binding.editeBtn.setOnClickListener {
            listener.onEditeBus(list[position]!!)
        }
    }
    class TripsHolder(item: View):RecyclerView.ViewHolder(item) {
        val binding= BusItemBinding.bind(item)
    }
}