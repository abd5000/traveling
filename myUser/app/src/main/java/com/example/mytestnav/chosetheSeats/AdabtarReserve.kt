package com.example.mytestnav.chosetheSeats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mytestnav.R
import com.example.mytestnav.databinding.SeatBussBinding

class AdabtarReserve(private val seatReserv:List<Int>,private val seatAll:List<Int>,private val listener: SeatLiesteniar,private val price:Int):RecyclerView.Adapter<AdabtarReserve.ReserveViewHolder>() {

private var selectCount=0
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
                if (selectCount<6){
                    holder.binding.number.setTextColor(ContextCompat.getColor(holder.binding.number.context,
                        R.color.blue
                    ))
                    selectCount++
                    selected.add(item)

                    listPosition.add(item)
                    listener.onCheckItem(+price,item)
                }else{
                    holder.binding.seat.isChecked=false
                    Toast.makeText(holder.binding.number.context,"You cannot select more than 6 seats",Toast.LENGTH_SHORT).show()
                }
            }else{
                selectCount--
                selected.remove(item)

                listPosition.remove(item)
                listener.onCheckItem(-price,item)
                holder.binding.number.setTextColor(ContextCompat.getColor(holder.binding.number.context,
                    R.color.black
                ))
            }
        }

        if (position < seatAll.size) {  // تأكد من أن الفهرس ضمن نطاق القائمة
            if (!seatReserv.contains(position+1)){
            holder.binding.seat.isEnabled = false
            holder.binding.number.setTextColor(ContextCompat.getColor(holder.binding.number.context,R.color.red))}

        }

    }

    override fun getItemCount(): Int {
       return seatAll.size
    }
    class ReserveViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val binding=SeatBussBinding.bind(itemView)
    }
}