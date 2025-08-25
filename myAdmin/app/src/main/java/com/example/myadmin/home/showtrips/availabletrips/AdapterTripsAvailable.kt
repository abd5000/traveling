package com.example.myadmin.home.showtrips.availabletrips

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myadmin.R
import com.example.myadmin.databinding.AvailablItemTripsBinding
import com.example.myadmin.home.tracking.TrackingFragment

class AdapterTripsAvailable(private val list: List<Data?>, private val listener: AvailableTripListener): RecyclerView.Adapter<AdapterTripsAvailable.TripsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripsHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.availabl_item_trips,parent,false)
        return TripsHolder(view)
    }

    override fun getItemCount()=list!!.size

    override fun onBindViewHolder(holder: TripsHolder, position: Int) {
        val cur= list!![position]
        holder.binding.apply {
            cityFrom.text="${cur!!.starting.toString().trim()}"
            cityTo.text="${cur!!.destination.toString().trim()}"
            timeFrom.text="${cur!!.tripTime.toString().trim()}"
            timeTo.text="${cur!!.arrivalTime.toString().trim()}"
            dateTrip.text="${cur.tripDate}"
            time.text=" ${cur!!.duration.toString().trim()}"
            bus.text=" ${cur!!.numberbus.toString().trim()}"
            typeTripe.text="${cur!!.typebus.toString().trim()}"
            price.text="   ${cur!!.price.toString().trim()}"
            seatsReserved.text=" ${cur!!.numberdisksisFalse!!.size.toString().trim()}"
        }
        if (cur!!.numberdisksisFalse!!.size==0||cur.isAvailable=="Now"){
            holder.binding.reserveBtn.isEnabled=false
            holder.binding.reserveBtn.background = (ContextCompat.getDrawable(holder.binding.reserveBtn.context,R.drawable.stat_date))
        }else{
            holder.binding.reserveBtn.isEnabled=true
            holder.binding.reserveBtn.background = (ContextCompat.getDrawable(holder.binding.reserveBtn.context,R.drawable.stat_shap_login))

        }
        if (cur.isAvailable=="Now"){
            holder.binding.location.visibility=View.VISIBLE
        }else{
            holder.binding.location.visibility=View.GONE
        }
      holder.binding.custmorsBtn.setOnClickListener {
            listener.onClickItem(list[position]!!)
        }
        holder.binding.reserveBtn.setOnClickListener {
            listener.onReserveItem(list[position]!!)
        }
    }
    class TripsHolder(item: View):RecyclerView.ViewHolder(item) {
        val binding= AvailablItemTripsBinding.bind(item)
    }
}