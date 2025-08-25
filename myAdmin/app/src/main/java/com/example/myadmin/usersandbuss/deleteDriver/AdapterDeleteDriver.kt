package com.example.myadmin.usersandbuss.deleteDriver

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myadmin.R

import com.example.myadmin.databinding.BusItemBinding
import com.example.myadmin.databinding.DriverItemBinding


class AdapterDeleteDriver(private val list: List<com.example.myadmin.usersandbuss.deleteDriver.Data?>?, private val listener:DeleteDriverListener): RecyclerView.Adapter<AdapterDeleteDriver.TripsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripsHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.driver_item,parent,false)
        return TripsHolder(view)
    }

    override fun getItemCount()=list!!.size

    override fun onBindViewHolder(holder: TripsHolder, position: Int) {
        val cur= list!![position]
        holder.binding.apply {
            number.text=" ${cur!!.phone}"
            driver.text="${cur.name}"
        }

        holder.binding.deleteBtn.setOnClickListener {
            listener.onReserveDriver(list[position]!!,position)
        }
        holder.binding.number.setOnClickListener {
            listener.onCallPhone(list[position]!!)
        }
    }
    class TripsHolder(item: View):RecyclerView.ViewHolder(item) {
        val binding= DriverItemBinding.bind(item)
    }
}