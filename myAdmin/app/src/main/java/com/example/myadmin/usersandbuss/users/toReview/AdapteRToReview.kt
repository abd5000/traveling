package com.example.myadmin.usersandbuss.users.toReview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myadmin.R
import com.example.myadmin.databinding.BlockedItemBinding
import com.example.myadmin.databinding.UserBlockedItemBinding


class AdapteRToReview(private val list: List<com.example.myadmin.usersandbuss.users.toReview.Data?>?, private val listener:BlockedUserListener): RecyclerView.Adapter<AdapteRToReview.TripsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripsHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.user_blocked_item,parent,false)
        return TripsHolder(view)
    }

    override fun getItemCount()=list!!.size

    override fun onBindViewHolder(holder: TripsHolder, position: Int) {
        val cur= list!![position]
        holder.binding.name.text=cur!!.fullname!!.trim()
        holder.binding.count.text="${cur.trips!!.size}"
        holder.binding.blockBtn.setOnClickListener {
            listener.onBlocked(list[position]!!,position)
        }
        holder.binding.detelsBtn.setOnClickListener {
            listener.onDetails(list[position]!!)
        }

    }
    class TripsHolder(item: View):RecyclerView.ViewHolder(item) {
        val binding= UserBlockedItemBinding.bind(item)
    }
}