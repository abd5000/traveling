package com.example.myadmin.usersandbuss.users.banned

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myadmin.R
import com.example.myadmin.databinding.BlockedItemBinding

import com.example.myadmin.databinding.BusItemBinding
import com.example.myadmin.databinding.DriverItemBinding
import com.example.myadmin.usersandbuss.users.banned.custoersBlocked.Data


class AdapterCancelBlocke(private val list: List<Data?>?, private val listener:CancleBlockedListener): RecyclerView.Adapter<AdapterCancelBlocke.TripsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripsHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.blocked_item,parent,false)
        return TripsHolder(view)
    }

    override fun getItemCount()=list!!.size

    override fun onBindViewHolder(holder: TripsHolder, position: Int) {
        val cur= list!![position]
        holder.binding.driver.text="${cur!!.fullName}"

        holder.binding.cancelBlockedBtn.setOnClickListener {
            listener.onCancelBlocked(list[position]!!,position)
        }

    }
    class TripsHolder(item: View):RecyclerView.ViewHolder(item) {
        val binding= BlockedItemBinding.bind(item)
    }
}