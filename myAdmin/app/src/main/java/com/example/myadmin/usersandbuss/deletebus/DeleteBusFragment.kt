package com.example.myadmin.usersandbuss.deletebus

import android.content.Context
import android.os.Bundle
import android.system.Os.remove
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.myadmin.R
import com.example.myadmin.databinding.FragmentDeleteBusBinding
import com.example.myadmin.home.edittrips.editeordelettrips.AdabterTrips
import com.example.myadmin.home.showtrips.availabletrips.AdapterTripsAvailable
import com.example.myadmin.usersandbuss.deletebus.allbuss.Data
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class DeleteBusFragment : Fragment(),DeleteBusListener {
    lateinit var binding:FragmentDeleteBusBinding
    private lateinit var adapter: AdapterDeleteBus
    private lateinit var token:String
    private val viewModel:ViewModelDeleteBus by viewModels()
    var buss= mutableListOf<com.example.myadmin.usersandbuss.deletebus.allbuss.Data?>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDeleteBusBinding.inflate(inflater,container,false)
        val sharedPreferenToken=requireContext().getSharedPreferences("onToken", Context.MODE_PRIVATE)
        token= sharedPreferenToken.getString("Token","")!!.trim()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {v->
            Navigation.findNavController(v).popBackStack()
        }
        viewModel.getAllBuss(token)
        viewModel.allBuss.observe(viewLifecycleOwner, Observer { result->
            kotlin.runCatching {
                Log.i("infodelettrip", "$result")
                if(result!=null &&result.success==true){
                    buss= result.data as MutableList<Data?>
                    if (buss.isEmpty()){
                        binding.notFound.visibility=View.VISIBLE
                        binding.errorText.text=result.message
                    }else{
                        binding.notFound.visibility=View.GONE

                    }
                    binding.recyclerview.visibility=View.VISIBLE
                     adapter= AdapterDeleteBus(buss,this)
                    adapter.notifyDataSetChanged()
                    binding.recyclerview.adapter=adapter

                }else{
                    binding.notFound.visibility=View.VISIBLE
                    binding.errorText.text= result!!.message
                }
            }
        })
    }



    override fun onReserveBus(bus: Data, position: Int) {
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
        description.text="Do you want to delete this bus?"
        description.textSize= 20F
        cancel.setOnClickListener {
            dialog.dismiss()
        }
        okButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        okButton.setOnClickListener {
            viewModel.deleteBus(bus.id!!)
            viewModel.delete.observe(viewLifecycleOwner, Observer { result->
                kotlin.runCatching {
                    if (result!=null && result.success==true){
                        if (buss.isEmpty()){
                            binding.notFound.visibility=View.VISIBLE
                            binding.errorText.text=result.message
                        }else{
                            buss.remove(bus)
                            binding.notFound.visibility=View.GONE
                            binding.recyclerview.visibility=View.VISIBLE
                            Log.i("infodeletebus", "$buss")
                            adapter= AdapterDeleteBus(buss,this)
                            adapter.notifyItemRemoved(position)
                            binding.recyclerview.adapter=adapter
                            dialog.dismiss()
                            Toast.makeText(requireContext(),result.message, Toast.LENGTH_SHORT).show()
                        }

                    }else if(result?.success==false){
                        Toast.makeText(requireContext(),result.message, Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                }
            })
        }
    }

    override fun onEditeBus(bus: Data) {
        val action=DeleteBusFragmentDirections.actionDeleteBusFragmentToEditeBusFragment(bus)
        findNavController().navigate(action)
    }

}