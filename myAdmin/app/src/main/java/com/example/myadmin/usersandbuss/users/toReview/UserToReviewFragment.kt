package com.example.myadmin.usersandbuss.users.toReview

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
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.myadmin.R
import com.example.myadmin.databinding.FragmentUserToReviewBinding
import com.example.myadmin.usersandbuss.UsersAndBussDirections
import com.example.myadmin.usersandbuss.users.UsersFragmentDirections
import com.example.myadmin.usersandbuss.users.banned.AdapterCancelBlocke
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class UserToReviewFragment : Fragment(),BlockedUserListener {
lateinit var binding:FragmentUserToReviewBinding
   private lateinit var adapter:AdapteRToReview
  private val viewModel: ViewModelToReview by viewModels()
    var customersTpReview= mutableListOf<Data?>()
    lateinit var token:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentUserToReviewBinding.inflate(inflater,container,false)
        val sharedPreferenToken=requireContext().getSharedPreferences("onToken", Context.MODE_PRIVATE)
        token= sharedPreferenToken.getString("Token","")!!.trim()
        viewModel.getAllUsersToReview(token)
        viewModel.users.observe(viewLifecycleOwner, Observer { result->
            kotlin.runCatching {
                if (result!=null && result.success==true)
                {
                    customersTpReview= result.data as MutableList<Data?>
                    if (customersTpReview.isEmpty()){
                        binding.notFound.visibility=View.VISIBLE
                        binding.errorText.text=result.message
                    }else{
                        binding.notFound.visibility=View.GONE
                        binding.recyclerview.visibility=View.VISIBLE
                        adapter= AdapteRToReview(customersTpReview,this)
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

    override fun onBlocked(customer: Data, position: Int) {
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
        description.text=" Do you want to block this customer?"
        description.textSize= 20F
        cancel.setOnClickListener {
            dialog.dismiss()
        }
        okButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        okButton.setOnClickListener {
            viewModel.blockCustomer(token,customer.id!!)
            viewModel.blockUser.observe(viewLifecycleOwner, Observer { result->
                kotlin.runCatching {
                    if (result!=null && result.success==true){
                        if (customersTpReview.isEmpty()){
                            binding.notFound.visibility=View.VISIBLE
                            binding.errorText.text=result.message
                        }else{
                            customersTpReview.remove(customer)
                            binding.notFound.visibility=View.GONE
                            binding.recyclerview.visibility=View.VISIBLE

                            adapter= AdapteRToReview(customersTpReview,this)
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

    override fun onDetails(customer: Data) {
       val action=UsersFragmentDirections.actionUsersFragmentToDetelsTripCustomerFragment(customer)
        findNavController().navigate(action)
    }


}