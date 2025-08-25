package com.example.myadmin.usersandbuss.deleteDriver

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer

import com.example.myadmin.R
import com.example.myadmin.databinding.FragmentDeleteDriverBinding
import com.example.myadmin.usersandbuss.deletebus.AdapterDeleteBus
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class DeleteDriverFragment : Fragment() ,DeleteDriverListener{
    lateinit var binding:FragmentDeleteDriverBinding
    private val viewModel:ViewModelDeleteDriver by viewModels()
    private lateinit var adapter:AdapterDeleteDriver
    private lateinit var token:String
    private var drivers= mutableListOf<Data?>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding= FragmentDeleteDriverBinding.inflate(inflater,container,false)
        val sharedPreferenToken=requireContext().getSharedPreferences("onToken", Context.MODE_PRIVATE)
        token= sharedPreferenToken.getString("Token","")!!.trim()
        viewModel.getAllDrivers(token)
        kotlin.runCatching {
        viewModel.drivers.observe(viewLifecycleOwner, Observer { result->
            if(result!=null && result.success==true){
                drivers= result.data as MutableList<Data?>
                if (drivers.isEmpty()){
                    binding.notFound.visibility=View.VISIBLE
                    binding.errorText.text=result.message
                }else{
                    binding.notFound.visibility=View.GONE
                    binding.recyclerview.visibility=View.VISIBLE
                    adapter= AdapterDeleteDriver(drivers,this)
                    adapter.notifyDataSetChanged()
                    binding.recyclerview.adapter=adapter
                }
            }else{
                binding.notFound.visibility=View.VISIBLE
                binding.errorText.text= result!!.message
            }
        })
        }
        return binding.root
    }

    override fun onReserveDriver(driver: Data, position: Int) {
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
        description.text="Do you want to delete this driver?"
        description.textSize= 20F
        cancel.setOnClickListener {
            dialog.dismiss()
        }
        okButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        okButton.setOnClickListener {
            viewModel.deleteDriver(driver.id!!)
            viewModel.delete.observe(viewLifecycleOwner, Observer { result->
                kotlin.runCatching {
                    if (result!=null && result.success==true){
                        if (drivers.isEmpty()){
                            binding.notFound.visibility=View.VISIBLE
                            binding.errorText.text=result.message
                        }else{
                            drivers.remove(driver)
                            binding.notFound.visibility=View.GONE
                            binding.recyclerview.visibility=View.VISIBLE

                            adapter= AdapterDeleteDriver(drivers,this)
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

    override fun onCallPhone(driver: Data) {
        val intent=Intent(Intent.ACTION_DIAL)
        intent.data=Uri.parse("tel:${driver.phone}")
        startActivity(intent)

    }
}