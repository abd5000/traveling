package com.example.myadmin.home.tracking

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myadmin.databinding.FragmentTrackingBinding
import com.example.myadmin.home.showtrips.availabletrips.AdapterTripsAvailable
import com.example.myadmin.home.showtrips.availabletrips.Data
import io.socket.client.IO
import io.socket.client.Socket

class TrackingFragment : Fragment(),TrackingTripListener {
    private lateinit var socket: Socket
  lateinit var binding:FragmentTrackingBinding
  private val viewModel:ViewModelTrackTrips by viewModels()
    var trips= mutableListOf<Data?>()
    private lateinit var token:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentTrackingBinding.inflate(inflater,container,false)
        val sharedPreferenToken=requireContext().getSharedPreferences("onToken", Context.MODE_PRIVATE)
        token= sharedPreferenToken.getString("Token","")!!.trim()
        viewModel.getTrackTrips(token)
        viewModel.tripsTracking.observe(viewLifecycleOwner, Observer { result->
            kotlin.runCatching {
                if(result!=null && result.success==true){
                    trips= result.data as MutableList<Data?>
                    Log.e("infoTrips", "${trips[0]!!.tripDate.toString()}", )
                    if (trips.isEmpty()){
                        binding.notFound.visibility=View.VISIBLE
                    }else{
                        binding.notFound.visibility=View.GONE
                    }
                    binding.recyclerview.visibility=View.VISIBLE
                    val adapter= AdapterTrackingTrips(trips,this)
                    adapter.notifyDataSetChanged()
                    binding.recyclerview.adapter=adapter

                }else{
                    binding.notFound.visibility=View.VISIBLE
                }
            }
        })
        return binding.root
    }

    override fun onTrackingTrip(trip: Data) {
        Log.i("driveridshow", "${trip.driverId}")
       val action=TrackingFragmentDirections.actionTrackingFragmentToMapsFragment2(trip.driverId!!)
        findNavController().navigate(action)

    }

}