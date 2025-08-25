package com.example.mydriver.home.detailstrip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mydriver.R
import com.example.mydriver.databinding.SeatBussBinding

class AdabtarReserve(private val seatReserv: List<Int?>?, private val seatAll:List<Int>):RecyclerView.Adapter<AdabtarReserve.ReserveViewHolder>() {

    private var selected= mutableListOf<Int>()
    private var listPosition= mutableListOf<Int>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReserveViewHolder {
       val view=LayoutInflater.from(parent.context).inflate(R.layout.seat_buss,parent,false)
        return ReserveViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReserveViewHolder, position: Int) {
        //ترقيم المقاعد
        holder.binding.number.text = "${position+1}"
        val item=seatAll[position]
        holder.binding.seat.isChecked=selected.contains(item)
        holder.binding.seat.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){

                    holder.binding.number.setTextColor(ContextCompat.getColor(holder.binding.number.context,
                        R.color.blue
                    ))

                    selected.add(item)

                    listPosition.add(item)


            }else{

                selected.remove(item)

                listPosition.remove(item)
                holder.binding.number.setTextColor(ContextCompat.getColor(holder.binding.number.context,
                    R.color.black
                ))
            }
        }

        if (position < seatAll.size) {  // تأكد من أن الفهرس ضمن نطاق القائمة
            if (!seatReserv!!.contains(position+1)){
            holder.binding.seat.isEnabled = false
            holder.binding.number.setTextColor(ContextCompat.getColor(holder.binding.number.context,
                R.color.red
            ))}

        }

    }

    override fun getItemCount(): Int {
       return seatAll.size
    }
    class ReserveViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val binding= SeatBussBinding.bind(itemView)
    }
}