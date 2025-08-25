package com.example.myadmin.home.showtrips.finishedtrips

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myadmin.R
import com.example.myadmin.databinding.FinishItemTripsBinding

class AdapterTripsFinished(private val list: List<Data?>): RecyclerView.Adapter<AdapterTripsFinished.TripsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripsHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.finish_item_trips,parent,false)
        return TripsHolder(view)
    }

    override fun getItemCount()= list.size

    override fun onBindViewHolder(holder: TripsHolder, position: Int) {
        val cur= list!![position]
        holder.binding.apply {
            cityFrom.text= cur!!.starting.toString().trim()
            cityTo.text= cur!!.destination.toString().trim()
            timeFrom.text= cur!!.tripTime.toString().trim()
            timeTo.text="${cur!!.arrivalTime.toString().trim()}"
            dateTrip.text="${cur.tripDate}"
            time.text=" ${cur!!.duration.toString().trim()}"
            bus.text=" ${cur!!.numberbus.toString().trim()}"
            typeTripe.text="${cur!!.typebus.toString().trim()}"
            price.text="   ${cur!!.price.toString().trim()}"
            seatsReserved.text=" ${cur!!.numberdisksisFalse!!.size.toString().trim()}"
            if (cur.rating==null){
            rating.text="0"}else{
            rating.text="${cur.rating}"}
            numberRauting.text="${cur.count}"


        }
    }
    class TripsHolder(item: View):RecyclerView.ViewHolder(item) {
        val binding= FinishItemTripsBinding.bind(item)
    }
}