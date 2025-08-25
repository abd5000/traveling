package com.example.mytestnav.mybook.paid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytestnav.R
import com.example.mytestnav.databinding.ItemMyReserveBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MyPaidAdabter(private val list:List<Data?>?,private val listener:TripClickLesener):RecyclerView.Adapter<MyPaidAdabter.hold>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): hold {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_my_reserve,parent,false)
        return hold(view)
    }

    override fun onBindViewHolder(holder: hold, position: Int) {
        val cush= list!![position]


        val format = SimpleDateFormat("yyyy-MM-dd")
        val date = format.parse(cush?.tripDate)
        val calendar = Calendar.getInstance()
        calendar.time = date
        val monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val yearOf = calendar.get(Calendar.YEAR)


        holder.binding.apply {
            company.text=cush?.company
            cityFrom.text=cush?.starting
            cityTo.text=cush?.destination
            timeStart.text=cush?.tripTime
            timeEnd.text=cush?.arrivalTime
            price.text="${cush?.price} SYP"
            mothe.text="$monthName"
            day.text="$dayOfMonth"
            year.text="$yearOf"

        }
        holder.binding.tripCard.setOnClickListener {
            listener.onCheckItem(list[position]!!,position)
        }


    }

    override fun getItemCount()= list!!.size

  inner  class hold(item:View):RecyclerView.ViewHolder(item) {
      val binding=ItemMyReserveBinding.bind(item)
    }
}