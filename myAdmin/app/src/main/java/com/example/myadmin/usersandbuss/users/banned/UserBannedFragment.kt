package com.example.myadmin.usersandbuss.users.banned

import android.content.Context
import android.os.Bundle
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
import com.example.myadmin.databinding.FragmentUserBannedBinding
import com.example.myadmin.usersandbuss.deleteDriver.AdapterDeleteDriver
import com.example.myadmin.usersandbuss.users.banned.custoersBlocked.Data
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class UserBannedFragment : Fragment() ,CancleBlockedListener{
lateinit var binding:FragmentUserBannedBinding
    private lateinit var adapter:AdapterCancelBlocke
private val viewModel:ViewModleBlockd by viewModels()
    var customersBlocked= mutableListOf<Data?>()
    lateinit var token:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding= FragmentUserBannedBinding.inflate(inflater,container,false)
        val sharedPreferenToken=requireContext().getSharedPreferences("onToken", Context.MODE_PRIVATE)
        token= sharedPreferenToken.getString("Token","")!!.trim()
        viewModel.getAllCustomersBlocked(token)
        viewModel.customers.observe(viewLifecycleOwner, Observer { result->
            kotlin.runCatching {
                if(result!=null && result.success==true){
                    customersBlocked= result.data as MutableList<Data?>
                    if (customersBlocked.isEmpty()){
                        binding.notFound.visibility=View.VISIBLE
                        binding.errorText.text=result.message
                    }else{
                        binding.notFound.visibility=View.GONE
                        binding.recyclerview.visibility=View.VISIBLE
                        adapter= AdapterCancelBlocke(customersBlocked,this)
                        adapter.notifyDataSetChanged()
                        binding.recyclerview.adapter=adapter
                    }
                }else{
                    binding.notFound.visibility=View.VISIBLE
                    binding.errorText.text= result!!.message
                }
            }
        })
        return binding.root
    }



    override fun onCancelBlocked(customer: Data, position: Int) {
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
        description.text="Do you want to unblock  this customer?"
        description.textSize= 20F
        cancel.setOnClickListener {
            dialog.dismiss()
        }
        okButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        okButton.setOnClickListener {
            viewModel.cancelBlockCustomer(token,customer.id!!)
            viewModel.cancel.observe(viewLifecycleOwner, Observer { result->
                kotlin.runCatching {
                    if (result!=null && result.success==true){
                        if (customersBlocked.isEmpty()){
                            binding.notFound.visibility=View.VISIBLE
                            binding.errorText.text=result.message
                        }else{
                            customersBlocked.remove(customer)
                            binding.notFound.visibility=View.GONE
                            binding.recyclerview.visibility=View.VISIBLE

                            adapter= AdapterCancelBlocke(customersBlocked,this)
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

}