package com.example.mytestnav.trips

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytestnav.R
import com.example.mytestnav.databinding.ItemTripsBinding
import java.math.RoundingMode

class AdabterTrips(val list: List<Data?>?, val listener:TripListener): RecyclerView.Adapter<AdabterTrips.TripsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripsHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_trips,parent,false)
        return TripsHolder(view)
    }

    override fun getItemCount()=list!!.size

    override fun onBindViewHolder(holder: TripsHolder, position: Int) {
        val cur= list!![position]
        holder.binding.apply {
           company.text=cur?.company
            typeTripe.text=cur?.typebus
            depTime.text="${cur?.tripTime}"
            arpTime.text="${cur?.arrivalTime}"
            seats.text="${cur?.numberdisksisFalse!!.size}"
            price.text="SYP  ${cur?.price}"
            flightDuration.text="${cur.duration} Hrs"
            if (cur.rating!=null){
                val rate=cur.rating
                val formatNumber=rate.toBigDecimal().setScale(1,RoundingMode.HALF_UP).toString()
                rating.text="$formatNumber"
                numberRauting.text="(${cur.countRating})"
            }else{
                rating.text="0"
                numberRauting.text="(0)"
            }

        }
      holder.binding.tripCard.setOnClickListener {
            listener.onClickItem(list[position]!!)
        }
    }
    class TripsHolder(item: View):RecyclerView.ViewHolder(item) {
        val binding=ItemTripsBinding.bind(item)
    }
}