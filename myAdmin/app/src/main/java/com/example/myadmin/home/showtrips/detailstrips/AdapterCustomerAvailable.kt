package com.example.myadmin.home.showtrips.detailstrips

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myadmin.R
import com.example.myadmin.databinding.CutomerItemBinding

class AdapterCustomerAvailable(private val list: List<com.example.myadmin.home.showtrips.detailstrips.Data?>, private val listener: ConfirmCustomerListener,private val paid:Boolean): RecyclerView.Adapter<AdapterCustomerAvailable.TripsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripsHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.cutomer_item,parent,false)
        return TripsHolder(view)
    }

    override fun getItemCount()=list!!.size

    override fun onBindViewHolder(holder: TripsHolder, position: Int) {
        val cur= list!![position]
        holder.binding.apply {
            name.text=cur!!.fullName
            priceCustomer.text=cur.totalprice.toString()
            var seat=""
            cur.numberdisk!!.forEach{item->
                seat+=",$item"
            }
        seat=seat.substring(1)
            seats.text=" $seat"
        }
        if (paid){
            holder.binding.cancelBtn.isEnabled=false
            holder.binding.cancelBtn.background = (ContextCompat.getDrawable(holder.binding.cancelBtn.context,R.drawable.stat_date))

            holder.binding.confirmBtn.isEnabled=false
            holder.binding.confirmBtn.background = (ContextCompat.getDrawable(holder.binding.confirmBtn.context,R.drawable.stat_date))

        }else{
            holder.binding.cancelBtn.isEnabled=true
            holder.binding.cancelBtn.background = (ContextCompat.getDrawable(holder.binding.cancelBtn.context,R.drawable.cancel_shap))


            holder.binding.confirmBtn.isEnabled=true
            holder.binding.confirmBtn.background = (ContextCompat.getDrawable(holder.binding.confirmBtn.context,R.drawable.stat_shap_login))

        }
      holder.binding.confirmBtn.setOnClickListener {
            listener.onClickItem(list[position]!!)
        }
        holder.binding.cancelBtn.setOnClickListener {
            listener.onCancelItem(list[position]!!)
        }
    }
    class TripsHolder(item: View):RecyclerView.ViewHolder(item) {
        val binding= CutomerItemBinding.bind(item)
    }
}