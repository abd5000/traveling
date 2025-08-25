package com.example.mytestnav.chosetheSeats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mytestnav.databinding.FragmentReserveBinding

class Reserve : Fragment(), SeatLiesteniar {
    lateinit var binding:FragmentReserveBinding
    private lateinit var  adabter: AdabtarReserve
    private val args:ReserveArgs by navArgs()
    private val listAll= mutableListOf<Int>()
    private val seatList= mutableListOf<Int>()
    private var price=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentReserveBinding.inflate(inflater,container,false)



        binding.TicketPrice.text="SYP ${args.price}"
        val numberSeat=args.NumberSeat.size
        binding.numberSeatNotReserves.text="$numberSeat"
        binding.company.text="${args.nameCompany}"
        binding.departCity.text="${args.cityFrom}"
        binding.arrivalCity.text="${args.cityTo}"
        binding.depart.text="${args.arraivTime}"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(listAll.isNullOrEmpty() ){
            if( args.typeTrip=="Vip"){
                for(i in 1..30){
                    listAll.add(i)
                }
            }else{
                for(i in 1..40){
                    listAll.add(i)
                }
            }
        }

        val listReserve=args.NumberSeat.asList()
        adabter= AdabtarReserve(listReserve,listAll,this,args.price)
        if (args.typeTrip=="Vip"){
            binding.recyclerview.layoutManager= GridLayoutManager(requireContext(),3)
        }else{
            binding.recyclerview.layoutManager= GridLayoutManager(requireContext(),4)
        }
        binding.recyclerview.adapter=adabter
        binding.recyclerview.addItemDecoration(SpanItemDecoration(30,args.typeTrip))
        adabter.notifyDataSetChanged()

    }
    override fun onResume() {
        super.onResume()

        binding.toolbar.setNavigationOnClickListener {v->
            Navigation.findNavController(v).popBackStack()
        }

        binding.checkOut.setOnClickListener {v->
            if (seatList.isNotEmpty()){
                var reserveSeats= mutableListOf<Int>()
                seatList.forEach{element->
                    reserveSeats.add(element)
                }
//               val toast= Toast.makeText(requireContext(),builder.toString(),Toast.LENGTH_SHORT)
//                toast.show()

                val action=ReserveDirections.actionReserveToConfirmReserve("${args.cityFrom}","${args.cityTo}","${args.date}","${args.time}","${args.nameCompany}",args.busNumber,args.price,reserveSeats.toIntArray(),args.tripId)
                Navigation.findNavController(v).navigate(action)
            }

        }

    }

    override fun onCheckItem(seat: Int, position: Int) {
        price += seat


        if (!seatList.contains(position)) seatList.add(position) else seatList.remove(position)

        binding.checkOut.isEnabled = seatList.size != 0
    }

}