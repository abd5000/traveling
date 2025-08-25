package com.example.myadmin.home.showtrips.reserve

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myadmin.databinding.FragmentReserveSeatsBinding

import com.example.myadmin.home.showtrips.reserve.reservapi.ViewModelSeats

class ReserveSeats : Fragment(),SeatLiesteniar {
    lateinit var binding:FragmentReserveSeatsBinding
    private lateinit var  adabter: AdabtarReserve
    private lateinit var token:String
    private val viewModel:ViewModelSeats by viewModels()
    private val args:ReserveSeatsArgs by navArgs()
    private val listAll= mutableListOf<Int>()
    private val seatList= mutableListOf<Int>()
    private var price=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentReserveSeatsBinding.inflate(inflater,container,false)
        binding.TicketPrice.text="SYP ${args.trip.price}"
        val numberSeat=args.trip.numberdisksisFalse!!.size
        binding.typeTripe.text= args.trip.typebus
        binding.numberSeatNotReserves.text="$numberSeat"
        binding.departCity.text=args.trip.starting
        binding.arrivalCity.text= args.trip.destination
        binding.depart.text= args.trip.tripTime
        val sharedPreferenToken=requireContext().getSharedPreferences("onToken", Context.MODE_PRIVATE)
        token= sharedPreferenToken.getString("Token","")!!.trim()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(listAll.isEmpty() ){
            if( args.trip.typebus=="Vip"){
                for(i in 1..30){
                    listAll.add(i)
                }
            }else{
                for(i in 1..40){
                    listAll.add(i)
                }
            }
        }

        val listReserve=args.trip.numberdisksisFalse
        adabter= AdabtarReserve(listReserve,listAll,this,args.trip.price!!)
        if (args.trip.typebus=="Vip"){
            binding.recyclerview.layoutManager= GridLayoutManager(requireContext(),3)
        }else{
            binding.recyclerview.layoutManager= GridLayoutManager(requireContext(),4)
        }
        binding.recyclerview.adapter=adabter
        binding.recyclerview.addItemDecoration(SpanItemDecoration(30,args.trip.typebus!!))
        adabter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()

        binding.toolbar.setNavigationOnClickListener {v->
            Navigation.findNavController(v).popBackStack()
        }

        binding.checkOut.setOnClickListener {v->
            if (seatList.isNotEmpty()){
                val reserveSeats= mutableListOf<Int>()
                seatList.forEach{element->
                    reserveSeats.add(element)
                }
                val action=ReserveSeatsDirections.actionReserveSeatsToReserveByAdminFragment(args.trip,reserveSeats.toIntArray())
                findNavController().navigate(action)

            }


        }

    }

    override fun onCheckItem(seat: Int, position: Int) {
        price += seat


        if (!seatList.contains(position)) seatList.add(position) else seatList.remove(position)

        binding.checkOut.isEnabled = seatList.size != 0
    }

}