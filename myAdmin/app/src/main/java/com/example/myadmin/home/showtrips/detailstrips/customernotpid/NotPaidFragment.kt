package com.example.myadmin.home.showtrips.detailstrips.customernotpid

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.myadmin.R
import com.example.myadmin.databinding.FragmentDetailsTripBinding
import com.example.myadmin.databinding.FragmentNotpaidBinding
import com.example.myadmin.home.showtrips.detailstrips.AdapterCustomerAvailable
import com.example.myadmin.home.showtrips.detailstrips.ConfirmCustomerListener
import com.example.myadmin.home.showtrips.detailstrips.CustomerTripFragmentArgs
import com.example.myadmin.home.showtrips.detailstrips.Data
import com.example.myadmin.home.showtrips.detailstrips.ViewModelDetails
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.math.log

class NotPaidFragment : Fragment(), ConfirmCustomerListener {
   lateinit var binding:FragmentNotpaidBinding
   private lateinit var token:String
    private val viewModel: ViewModelDetails by viewModels()
    private var tripId=0
    private var trips= mutableListOf<Data?>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentNotpaidBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferenToken=requireContext().getSharedPreferences("onToken", Context.MODE_PRIVATE)
        token= sharedPreferenToken.getString("Token","")!!.trim()

        val sharedPreferenTrip=requireContext().getSharedPreferences("onTrip", Context.MODE_PRIVATE)
        tripId=sharedPreferenTrip.getInt("tripId",0)
        viewModel.getCustomer(token, tripId)
        viewModel.details.observe(viewLifecycleOwner, Observer { result->
            kotlin.runCatching {
                Log.i("infocustom", "$result")
                if(result!=null && result.success==true){
                    trips= result.data as MutableList<Data?>
                    if (trips.isEmpty()){
                        binding.notFound.visibility=View.VISIBLE
                        binding.masseg.text=result.message
                    }else{


                        binding.notFound.visibility=View.GONE
                        binding.recyclerview.visibility=View.VISIBLE
                        val adapter= AdapterCustomerAvailable(trips,this,paid = false)
                        adapter.notifyDataSetChanged()
                        binding.recyclerview.adapter=adapter
                    }


                }else{
                    binding.notFound.visibility=View.VISIBLE
                    binding.masseg.text= result!!.message
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()


        binding.search.addTextChangedListener{
            if (binding.search.text.isNotEmpty()){
                viewModel.filterCustomerBuySeat(tripId,binding.search.text.toString().trim().toInt())
                kotlin.runCatching {
                    viewModel.filterCustomer.observe(viewLifecycleOwner, Observer { result->
                        Log.i("infocustomer", "$result.")
                        if(result!=null && result.success==true){
                            trips= result.data as MutableList<Data?>
                            if (trips.isEmpty()){
                                binding.notFound.visibility=View.VISIBLE
                                binding.masseg.text= result!!.message
                            }else{

                                binding.notFound.visibility=View.GONE
                                binding.recyclerview.visibility=View.VISIBLE
                                val adapter= AdapterCustomerAvailable(trips,this,paid = false)
                                binding.recyclerview.adapter=adapter
                                adapter.notifyDataSetChanged()
                            }


                        }else{
                            binding.notFound.visibility=View.VISIBLE
                            binding.masseg.text= result!!.message
                        }

                    })
                }
            }else{
                viewModel.getCustomer(token,tripId)
                viewModel.details.observe(viewLifecycleOwner, Observer { result->
                    kotlin.runCatching {
                        if(result!=null && result.success==true){
                            trips= result.data as MutableList<Data?>
                            if (trips.isEmpty()){
                                binding.notFound.visibility=View.VISIBLE
                                binding.masseg.text= result!!.message
                            }else{


                                binding.notFound.visibility=View.GONE
                                binding.recyclerview.visibility=View.VISIBLE
                                val adapter= AdapterCustomerAvailable(trips,this,false)
                                adapter.notifyDataSetChanged()
                                binding.recyclerview.adapter=adapter
                            }


                        }else{
                            binding.notFound.visibility=View.VISIBLE
                            binding.masseg.text= result!!.message
                        }
                    }
                })
            }
        }
    }

    override fun onClickItem(customer: Data) {
        val view=  View.inflate(requireContext(), R.layout.dialog_reserv,null)
        val builder= MaterialAlertDialogBuilder(requireContext())
        builder.setView(view)
        val dialog=builder.create()
        dialog.setCancelable(false)
        dialog.show()
        val cancel=view.findViewById<Button>(R.id.cancel_reserve)
        val okButton=view.findViewById<Button>(R.id.confirm_reserve)
        val titel=view.findViewById<TextView>(R.id.titel)
        val description=view.findViewById<TextView>(R.id.text_descrptioj)
        titel.visibility=View.GONE
        description.text="Do you want to confirem this reserve?"
        description.textSize= 20F
        cancel.setOnClickListener {
            dialog.dismiss()
        }
        okButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
        okButton.setOnClickListener {
            viewModel.confirmReserve(customer.id!!,tripId)
            viewModel.confirm.observe(viewLifecycleOwner, Observer { result->
                kotlin.runCatching {
                    if(result!=null && result.success==true){
                        Toast.makeText(requireContext(),result.message, Toast.LENGTH_SHORT).show()
                        viewModel.getCustomer(token,tripId)
                        viewModel.details.observe(viewLifecycleOwner, Observer { result->
                            kotlin.runCatching {
                                if(result!=null && result.success==true){
                                    trips= result.data as MutableList<Data?>
                                    if (trips.isEmpty()){
                                        binding.notFound.visibility=View.VISIBLE
                                        binding.masseg.text= result!!.message
                                    }else{


                                        binding.notFound.visibility=View.GONE
                                        binding.recyclerview.visibility=View.VISIBLE
                                        val adapter= AdapterCustomerAvailable(trips,this,false)
                                        adapter.notifyDataSetChanged()
                                        binding.recyclerview.adapter=adapter
                                    }


                                }else{
                                    binding.notFound.visibility=View.VISIBLE
                                    binding.masseg.text= result!!.message
                                }
                            }
                        })
                        dialog.dismiss()
                    }else{
                        Toast.makeText(requireContext(), result!!.message, Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                }
            })
        }
    }

    override fun onCancelItem(customer: Data) {
        val view=  View.inflate(requireContext(), R.layout.dialog_reserv,null)
        val builder= MaterialAlertDialogBuilder(requireContext())
        builder.setView(view)
        val dialog=builder.create()
        dialog.setCancelable(false)
        dialog.show()
        val cancel=view.findViewById<Button>(R.id.cancel_reserve)
        val okButton=view.findViewById<Button>(R.id.confirm_reserve)
        val titel=view.findViewById<TextView>(R.id.titel)
        val description=view.findViewById<TextView>(R.id.text_descrptioj)
        titel.visibility=View.GONE
        description.text="Do you want to cancel this reserve?"
        description.textSize= 20F
        cancel.setOnClickListener {
            dialog.dismiss()
        }
        okButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
        okButton.setOnClickListener {
            viewModel.cancelCustomerReserve(customer.id!!,tripId)
            viewModel.cancel.observe(viewLifecycleOwner, Observer { result->
                kotlin.runCatching {
                    if(result!=null && result.success==true){
                        Toast.makeText(requireContext(),result.message,Toast.LENGTH_SHORT).show()
                        viewModel.getCustomer(token,tripId)
                        viewModel.details.observe(viewLifecycleOwner, Observer { result->
                            kotlin.runCatching {
                                if(result!=null && result.success==true){
                                    trips= result.data as MutableList<Data?>
                                    if (trips.isEmpty()){
                                        binding.notFound.visibility=View.VISIBLE
                                        binding.masseg.text= result!!.message
                                    }else{


                                        binding.notFound.visibility=View.GONE
                                        binding.recyclerview.visibility=View.VISIBLE
                                        val adapter= AdapterCustomerAvailable(trips,this,false)
                                        adapter.notifyDataSetChanged()
                                        binding.recyclerview.adapter=adapter
                                    }


                                }else{
                                    binding.notFound.visibility=View.VISIBLE
                                    binding.masseg.text= result!!.message
                                }
                            }
                        })
                        dialog.dismiss()
                    }else{
                        Toast.makeText(requireContext(), result!!.message,Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                }
            })
        }
    }

}