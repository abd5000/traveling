package com.example.myadmin.home.edittrips.editeordelettrips

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myadmin.R
import com.example.myadmin.databinding.ItemTripsBinding
import com.example.myadmin.home.showtrips.availabletrips.Data
import java.math.RoundingMode

class AdabterTrips(private val list: List<Data?>?, private val listener:TripListener ,private val deleteListener:DeleteLestener): RecyclerView.Adapter<AdabterTrips.TripsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripsHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_trips,parent,false)
        return TripsHolder(view)
    }

    override fun getItemCount()=list!!.size

    override fun onBindViewHolder(holder: TripsHolder, position: Int) {
        val cur= list!![position]
        holder.binding.apply {
            timeFrom.text= "${cur!!.tripTime.toString().trim()}"
            timeTo.text="${cur.arrivalTime.toString().trim()}"
            cityFrom.text=cur.starting.toString().trim()
            cityTo.text=cur.destination.toString().trim()
            dateTrip.text="${cur.tripDate.toString().trim()}"
            time.text=" ${cur.duration.toString().trim()}"
            bus.text=" ${cur.numberbus.toString().trim()}"
            typeTripe.text="${cur.typebus.toString().trim()}"
            price.text="  ${cur.price.toString().trim()}"
            seatsReserved.text=" ${cur.numberdisksisFalse!!.size.toString().trim()}"

        }
      holder.binding.editeBtn.setOnClickListener {
            listener.onClickItem(list[position]!!)
        }
        holder.binding.delete.setOnClickListener {
            deleteListener.onDelete(list[position]!!)
        }
    }
    class TripsHolder(item: View):RecyclerView.ViewHolder(item) {
        val binding= ItemTripsBinding.bind(item)
    }
}