package com.example.mytestnav.mybook.notpaid

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
import com.example.mytestnav.databinding.FragmentNotPaidBinding
import com.example.mytestnav.mybook.MyBookDirections
import com.example.mytestnav.mybook.paid.Data
import com.example.mytestnav.mybook.paid.MyPaidAdabter
import com.example.mytestnav.mybook.paid.TripClickLesener

class NotPaid(
): Fragment(),TripClickLesener{
    lateinit var binding:FragmentNotPaidBinding
    var positionDelete: Int? =null
    private val viewModel:ViewModelNotPaid by viewModels()
    lateinit var  adabter:MyPaidAdabter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentNotPaidBinding.inflate(inflater,container,false)

        return binding.root
    }



    override fun onResume() {
        super.onResume()
            val sharedPreferenToken =
                requireContext().getSharedPreferences("onToken", Context.MODE_PRIVATE)
            val token = sharedPreferenToken.getString("Token", "")!!.trim()
        viewModel.notPaid(token)

        viewModel.tripNotPaid.observe(viewLifecycleOwner, Observer { result ->
            kotlin.runCatching {

                if (result != null) {
                    binding.notFound.visibility=View.VISIBLE
                    binding.textError.text=result.message
                    if (result.success == true && !result.data.isNullOrEmpty()) {
                        binding.notFound.visibility=View.GONE
                        val trips = result.data
                        adabter = MyPaidAdabter(trips, this)
                        binding.recyclerview.adapter = adabter
                        adabter.notifyDataSetChanged()
                    } else if (result.success==false) {
                        binding.notFound.visibility=View.VISIBLE
                        binding.textError.text=result.message
                    }
                }
            }
        })

    }




    override fun onCheckItem(trip: Data,position: Int) {
        val list= trip.numberdisksisnotpaid
        positionDelete=position
        Log.e("listone", "${list!![0]}", )
        val number= IntArray(list.size)
        for (i in list.indices){
            number[i]= list[i]!!

        }

        val navController=findNavController()
        val action=MyBookDirections.actionMyBook2ToNotPaidDetails("${trip.starting}","${trip.destination}","${trip.tripDate}","${trip.tripTime}","${trip.company}",
            trip.numberbus!!,trip.price!!,number,"${trip.typebus}",trip.id!!
   )
        navController.navigate(action)

//        findNavController().navigate(R.id.action_myBook2_to_notPaidDetails)

//        findNavController().navigate(R.id.action_myBook2_to_notPaidDetails)


    }




}