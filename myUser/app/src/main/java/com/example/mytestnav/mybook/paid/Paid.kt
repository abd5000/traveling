package com.example.mytestnav.mybook.paid

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.mytestnav.databinding.FragmentPaidBinding
import com.example.mytestnav.mybook.MyBookDirections

class Paid : Fragment(), TripClickLesener {
    lateinit var binding:FragmentPaidBinding
    lateinit var adapter: MyPaidAdabter
    private val viewModel:ViewModelPaid by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentPaidBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferenToken=requireContext().getSharedPreferences("onToken", Context.MODE_PRIVATE)
        val token= sharedPreferenToken.getString("Token","")!!

        viewModel.paid(token)
        viewModel.tripsPaid
            .observe(viewLifecycleOwner, Observer { result->
            kotlin.runCatching {
            if(result!=null){
                binding.notFound.visibility=View.VISIBLE
                binding.textError.text=result.message
            if(result.success==true && result.data!!.isNotEmpty()){
                binding.notFound.visibility=View.GONE
                val trips=result.data
                binding.recyclerview.visibility=View.VISIBLE
                val adapter= MyPaidAdabter(trips,this)
                binding.recyclerview.adapter=adapter
                adapter.notifyDataSetChanged()
            }else if(result.data!!.isEmpty()||result.success==false){
                binding.notFound.visibility=View.VISIBLE
                binding.textError.text=result.message
            }
            }
            }
        })


    }

    override fun onCheckItem(trip: Data, position: Int) {
        val list= trip.numberdisksispaid
        Log.e("listone", "${list!![0]}", )
        val number= IntArray(list.size)
        for (i in list.indices){
            number[i]= list[i]!!

        }

        val navController=findNavController()
        val action=MyBookDirections.actionMyBook2ToPaidDetails("${trip.starting}","${trip.destination}","${trip.tripDate}","${trip.tripTime}","${trip.company}",
            trip.numberbus!!,trip.price!!,number,"${trip.typebus}",trip.id!!
        )
        navController.navigate(action)
    }

}