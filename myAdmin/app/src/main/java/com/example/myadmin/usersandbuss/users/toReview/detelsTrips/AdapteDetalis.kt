package com.example.myadmin.usersandbuss.users.toReview.detelsTrips

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myadmin.R
import com.example.myadmin.databinding.BlockedItemBinding
import com.example.myadmin.databinding.UserBlockedItemBinding
import com.example.myadmin.databinding.UserTripItemBinding
import com.example.myadmin.usersandbuss.users.toReview.Trip


class AdapteDetalis(private val list: List<Trip?>?): RecyclerView.Adapter<AdapteDetalis.TripsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripsHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.user_trip_item,parent,false)
        return TripsHolder(view)
    }

    override fun getItemCount()=list!!.size

    override fun onBindViewHolder(holder: TripsHolder, position: Int) {
        val cur= list!![position]
        holder.binding.dateTrip.text=cur!!.tripDate!!.trim()
        holder.binding.tripNumber.text="${position+1}"
        holder.binding.bus.text="${cur.numberbus!!}"


    }
    class TripsHolder(item: View):RecyclerView.ViewHolder(item) {
        val binding= UserTripItemBinding.bind(item)
    }
}